package com.ytu.objectDetection.controller;

import com.ytu.objectDetection.service.ex.*;
import com.ytu.objectDetection.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 控制器基类
 */
public class BaseController {

    /**
     * 操作结果的“成功”状态
     */
    public static final Integer SUCCESS = 2000;

    /**
     * 操作结果的“错误”状态
     */
    public static final Integer ERROR = 4444;

    /**
     * 确定文件名
     * @return 文件名
     */
    protected String getFilename(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex != -1) {
            suffix = originalFilename.substring(beginIndex);
        }
        String filename = UUID.randomUUID().toString() + suffix;
        return filename;
    }

    /**
     * 执行CMD 命令
     * @param cmd cmd字符串
     */
    protected void executeCMD(String cmd){
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            process.exitValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handlerException(Throwable e) {
        JsonResult<Void> jr = new JsonResult<>();
        jr.setMessage(e.getMessage());

        if (e instanceof FileEmptyException) {
            // 6000-上传空文件异常
            jr.setState(6000);
        }else if (e instanceof FileSizeException){
            // 6001-上传文件大小受限异常
            jr.setState(6001);
        }else if (e instanceof FileTypeException){
            // 6002-上传文件格式不规范异常
            jr.setState(6002);
        }else if (e instanceof FileUploadIOException){
            // 6003-文件保存时出现IO异常
            jr.setState(6003);
        }else if (e instanceof FileUploadStateException){
            // 6004-文件状态异常
            jr.setState(6004);
        }

        return jr;
    }

}
