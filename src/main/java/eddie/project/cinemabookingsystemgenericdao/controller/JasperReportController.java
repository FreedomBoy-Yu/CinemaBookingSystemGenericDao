package eddie.project.cinemabookingsystemgenericdao.controller;

import eddie.project.cinemabookingsystemgenericdao.service.impl.JasperReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/reports")
public class JasperReportController {

    private final JasperReportService jasperReportService;

    public JasperReportController(JasperReportService jasperReportService) {
        this.jasperReportService = jasperReportService;
    }

    /**
     * 📌 下載「所有書籍」報表
     */
    @GetMapping("/download/allbook")
    public ResponseEntity<byte[]> downloadAllBookReport() {
        return generateReportResponse("allbook", new HashMap<>());
    }

    /**
     * 📌 下載「特定狀態書籍」報表（`status` 是數字類型）
     */
    @GetMapping("/download/statusbook")
    public ResponseEntity<byte[]> downloadStatusBookReport(@RequestParam Integer status) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("status", status); // 參數傳遞給 JasperReports
        return generateReportResponse("findStatusBooks", parameters);
    }

    /**
     * 📌 下載「特定日期範圍內的銷售報表」
     */
    @GetMapping("/download/salesreport")
    public ResponseEntity<byte[]> downloadSalesReport(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);
        return generateReportResponse("salesReport", parameters);
    }

    /**
     * 📌 通用方法 - 產生報表並回傳 PDF
     */
    private ResponseEntity<byte[]> generateReportResponse(String reportName, HashMap<String, Object> parameters) {
        try {
            byte[] pdfBytes = jasperReportService.generateReport(reportName, parameters);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportName + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(("報表生成失敗: " + e.getMessage()).getBytes());
        }
    }
}
