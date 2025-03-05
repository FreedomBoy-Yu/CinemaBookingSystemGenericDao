package eddie.project.cinemabookingsystemgenericdao.controller;

import eddie.project.cinemabookingsystemgenericdao.service.impl.JasperReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class JasperReportController {

    private final JasperReportService jasperReportService;

    public JasperReportController(JasperReportService jasperReportService) {
        this.jasperReportService = jasperReportService;
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadReport() {
        try {
            // 取得報表 PDF 的 byte[]
            byte[] pdfBytes = jasperReportService.generateReport();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf") // 強制下載 PDF
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace(); // ✅ 列印錯誤資訊幫助除錯
            return ResponseEntity.internalServerError().body(("報表生成失敗: " + e.getMessage()).getBytes());
        }
    }
}
