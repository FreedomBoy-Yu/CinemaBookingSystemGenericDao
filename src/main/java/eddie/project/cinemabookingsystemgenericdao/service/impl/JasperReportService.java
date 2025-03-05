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
        registerCustomFont();
    }

    /**
     * ✅ 註冊自訂字型，讓 JasperReports 能夠識別 utf-8 字型
     */
    private void registerCustomFont() {
        try {
            // 讀取 utf-8.jar 內的字型
            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/utf-8.ttf");
            if (fontStream == null) {
                throw new RuntimeException("❌ 找不到字型檔案 utf-8.ttf，請確保它放在 src/main/resources/fonts/ 資料夾");
            }

            // 創建字型並註冊到 JVM
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            System.out.println("✅ 自訂字型註冊成功：" + customFont.getFontName());

        } catch (FontFormatException | IOException e) {
            System.err.println("❌ 自訂字型載入失敗：" + e.getMessage());
        }
    }

    /**
     *  產生 PDF 報表，並確保 utf-8 字型可以使用
     */
    public byte[] generateReport() throws Exception {
        // 1️⃣ 檢查報表檔案是否存在
        InputStream reportStream = getClass().getClassLoader().getResourceAsStream("reports/allbook.jrxml");
        if (reportStream == null) {
            throw new RuntimeException("❌ 找不到報表文件 allbook.jrxml，請檢查 src/main/resources/reports/ 是否有此檔案");
        }

        // 2️⃣ 編譯 .jrxml 為 .jasper
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // 3️⃣ 設定參數，讓報表支援 UTF-8 字型
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(JRParameter.REPORT_LOCALE, java.util.Locale.TAIWAN);
        parameters.put("REPORT_FONT", "utf-8");

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
