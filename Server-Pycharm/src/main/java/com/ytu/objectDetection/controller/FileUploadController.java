package com.ytu.objectDetection.controller;

import com.ytu.objectDetection.service.ex.*;
import com.ytu.objectDetection.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传控制器类
 */
@RestController
@RequestMapping("files")
public class FileUploadController extends BaseController{

    /**
     * 全局变量：暂存图片名字
     */
    public static String TMP_FILE_NAME;
//    public static String TMP_FILE_NAME_out;
    /**
     * 上传的图片的最大大小
     */
    private static final long IMAGE_MAX_SIZE = 10 * 1024 * 1024;
    /**
     * 上传时允许的文件的类型
     */
    private static final List<String> IMAGE_CONTENT_TYPES = new ArrayList<>();
    /**
     * 初始化上传时允许的文件的类型
     */
    static {
        IMAGE_CONTENT_TYPES.add("image/jpeg");
        IMAGE_CONTENT_TYPES.add("image/png");
        IMAGE_CONTENT_TYPES.add("image/jpg");
    }

    /**
     * 图片上传
     * @param file 文件参数
     * @return 文件上传正常的状态码
     */
    @RequestMapping("upload")
    public JsonResult<Void> upload(@RequestParam("originImg") MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("Upload failed！Please select a valid file！");
        }

        // 检查文件大小
        if (file.getSize() > IMAGE_MAX_SIZE) {
            throw new FileSizeException("Upload failed！Files larger than" + (IMAGE_MAX_SIZE / 1024) + "KB are not allowed！");
        }

        // 检查文件类型
        if (!IMAGE_CONTENT_TYPES.contains(file.getContentType())) {
            throw new FileTypeException("Upload failed！Only the following types of image files are allowed：" + IMAGE_CONTENT_TYPES);
        }

        // 确定文件夹
        //String dirPath = request.getServletContext().getRealPath("upload");
        // 确定文件夹,存放至指定文件夹
        String dirPath = "C:\\Users\\muxiaoyu\\Desktop\\Leeds\\Project\\reference_code\\test\\data";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 确定文件名
        String filename = getFilename(file);
        System.out.println("filename："+filename);
        //将文件名字暂存至全局变量
        TMP_FILE_NAME=filename;
        // 执行保存
        File dest = new File(dir, filename);
        try {
            file.transferTo(dest);
            System.out.println("Saved successfully, file path："+dirPath);
        } catch (IllegalStateException e) {
            throw new FileUploadStateException("Upload failed！Check that the original file exists and can be accessed！");
        } catch (IOException e) {
            throw new FileUploadIOException("Upload failed！An unknown error occurred while reading data！");
        }
        return new JsonResult<>(SUCCESS);


    }

    /**
     * 图片检测
     * @return 图片检测成功状态码
     */
    @RequestMapping("detect")
    public JsonResult<Void> detect() {
        //TMP_FILE_NAME
        String fileName=TMP_FILE_NAME;
        if(TMP_FILE_NAME==null){
            throw new FileEmptyException("Please upload the file first！");
        }
        System.out.println("image name："+fileName);
        String cmd="cmd /c c: && cd C:\\Users\\muxiaoyu\\Desktop\\Leeds\\Project\\reference_code\\test && activate py39 && python detect.py --image ./data/"+fileName;
//        String cmd="cmd /c c: && cd C:\\Users\\muxiaoyu\\Desktop\\Leeds\\Project\\reference_code\\lihao\\yolov3-tf2-master-2 && activate py39 && python detect.py --image ./data/"+fileName;
        System.out.println("command："+cmd);
        //执行CMD命令
        executeCMD(cmd);
        return new JsonResult<>(SUCCESS);


    }

    /**
     * Real-time detection
     * @return 图片检测成功状态码
     */
    @RequestMapping("realDetect")
    public JsonResult<Void> detectReal() {
        String cmd="cmd /c c: && cd C:\\Users\\muxiaoyu\\Desktop\\Leeds\\Project\\reference_code\\test && activate py39 && python detect_video.py --video 0";
//        String cmd="cmd /c c: && cd C:\\Users\\muxiaoyu\\Desktop\\Leeds\\Project\\reference_code\\lihao\\yolov3-tf2-master-2 && activate py39 && python detect_video.py --video 0";
        System.out.println("command："+cmd);
        //执行CMD命令
        executeCMD(cmd);
        return new JsonResult<>(SUCCESS);
    }

    /**
     * 图片显示
     * @return 图片路径
     */
    @RequestMapping("show")
    public JsonResult<String[]> showImg() {


        String data[]=new String[2];
        data[0]="/file/"+TMP_FILE_NAME;
        data[1]="/file/output/"+TMP_FILE_NAME;
//        data[0]="/file/"+TMP_FILE_NAME;
//        data[1]="/file/output.jpg";


        return new JsonResult<>(SUCCESS,data);
    }

}
