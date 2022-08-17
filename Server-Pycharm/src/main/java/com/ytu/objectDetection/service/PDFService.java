package com.ytu.objectDetection.service;

import org.springframework.http.ResponseEntity;

/**
 * PDF 文件处理接口文件
 */
public interface PDFService {

    /**
     * PDF 文件导出
     */
    ResponseEntity<?> export(String originImg,String detectImg);

}
