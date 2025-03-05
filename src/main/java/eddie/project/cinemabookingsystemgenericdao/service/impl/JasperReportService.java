package eddie.project.cinemabookingsystemgenericdao.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fonts.FontUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.*;
import java.sql.Connection;
import java.util.HashMap;

@Service
public class JasperReportService {

    private final DataSource dataSource;

    public JasperReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 生成報表，可支援帶參數
     * @param reportName 報表名稱（不含副檔名）
     * @param parameters 報表參數（可為空）
     * @return PDF 檔案 byte[]
     */

    public byte[] generateReport(String reportName, HashMap<String, Object> parameters) throws Exception {
        // 1️⃣ 檢查報表檔案是否存在
        InputStream reportStream = getClass().getClassLoader().getResourceAsStream("reports/" + reportName + ".jrxml");
        if (reportStream == null) {
            throw new RuntimeException("❌ 找不到報表文件 " + reportName + ".jrxml，請檢查 src/main/resources/reports/ 是否有此檔案");
        }

        // 2️⃣ 編譯 .jrxml 為 .jasper
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // 3️⃣ 設定參數（如果為 null，則用空的 HashMap）
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        parameters.put(JRParameter.REPORT_LOCALE, java.util.Locale.TAIWAN);

        // 4️⃣ 取得資料庫連線
        try (Connection connection = dataSource.getConnection()) {

            // 5️⃣ 填充報表
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // 6️⃣ 轉換為 PDF 並存入 ByteArrayOutputStream
            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, pdfStream);

            // 7️⃣ 回傳 PDF 檔案的 byte[]
            return pdfStream.toByteArray();
        }
    }
}

