package com.ytu.objectDetection.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * PDF 文件导出配置文件
 */
@Configuration
@Data
public class PDFExportConfig {

    /**
     * 宋体字体文件相对路径
     */
    @Value("${pdfExport.fontSimsun}")
    private String fontSimsun;

    /**
     * PDF导出模板文件相对路径
     */
    @Value("${pdfExport.employeeKpiFtl}")
    private String employeeKpiFtl;

}

