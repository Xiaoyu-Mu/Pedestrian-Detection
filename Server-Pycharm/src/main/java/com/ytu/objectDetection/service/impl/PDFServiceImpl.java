package com.ytu.objectDetection.service.impl;


import com.ytu.objectDetection.config.PDFExportConfig;
import com.ytu.objectDetection.service.PDFService;
import com.ytu.objectDetection.util.PDFUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * PDF接口处理实现类
 */
@Service
public class PDFServiceImpl implements PDFService {

    @Autowired
    private PDFExportConfig pdfExportConfig;

    /**
     * PDF 文件导出
     */
    @Override
    public ResponseEntity<?> export(String originImg,String detectImg) {
        HttpHeaders headers = new HttpHeaders();

        //数据导出(PDF 格式)
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("originImg",originImg);
        dataMap.put("detectImg",detectImg);

        String htmlStr = PDFUtil.freemarkerRender(dataMap, pdfExportConfig.getEmployeeKpiFtl());
        byte[] pdfBytes = PDFUtil.createPDF(htmlStr, pdfExportConfig.getFontSimsun());
        if (pdfBytes != null && pdfBytes.length > 0) {
            String fileName = System.currentTimeMillis() + (int) (Math.random() * 90000 + 10000) + ".pdf";
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(pdfBytes, headers, HttpStatus.OK);
        }

        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<String>("{ \"status\" : \"404\", \"message\" : \"not found\" }",
                headers, HttpStatus.NOT_FOUND);
    }
}