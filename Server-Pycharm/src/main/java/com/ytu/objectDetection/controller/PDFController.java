package com.ytu.objectDetection.controller;

import com.ytu.objectDetection.service.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PDF 文件处理 控制器类
 */
@RestController
@RequestMapping("pdf")
public class PDFController extends BaseController{

    @Autowired
    private PDFService pdfService;

    /**
     * PDF 文件导出
     * http://localhost:8080/pdf/dog01.jpg/export/output.jpg
     * 使用 a 链接即可下载;如果为 ajax/vue,则需要转换为 form 表单格式
     * eg: http://${apiAddress}/api/demo/common/export?key1=${key}&key2=${key2}
     * http://localhost:8080/pdf/export?originImg=dog01.jpg&detectImg=output.jpg
     */
    @RequestMapping("export")
    public ResponseEntity<?> export(String originImg,
                                    String detectImg){
        System.out.println(originImg+"||"+detectImg);
        try {
            ResponseEntity<?> responseEntity = pdfService.export(originImg,detectImg);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<String>("{ \"status\" : \"404\", \"message\" : \"not found\" }",
                headers, HttpStatus.NOT_FOUND);
    }

}