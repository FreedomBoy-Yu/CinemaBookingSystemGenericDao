package eddie.project.cinemabookingsystemgenericdao.controller;

import eddie.project.cinemabookingsystemgenericdao.service.impl.JasperReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/reports")
public class JasperReportController {

    private final JasperReportService jasperReportService;

    public JasperReportController(JasperReportService jasperReportService) {
        this.jasperReportService = jasperReportService;
    }

    /**
     * 📌 下載「所有書籍」報表
     */
    @GetMapping("/download/allbook")//done
    public ResponseEntity<byte[]> downloadAllBookReport() {
        return generateReportResponse("allbook", new HashMap<>());
    }

    /**
     * 📌 下載「特定狀態書籍」報表（`status` 是數字類型）
     */
    @GetMapping("/download/statusbook")//被取代了
    public ResponseEntity<byte[]> downloadStatusBookReport(@RequestParam Integer status) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("status", status); // 參數傳遞給 JasperReports
        return generateReportResponse("findStatusBooks", parameters);
    }

    /**
     * 📌 下載「特定日期範圍內的銷售報表」
     */
    @GetMapping("/download/daterange")
    public ResponseEntity<byte[]> downloadDateRange(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("startDate", new java.sql.Date(startDate.getTime()));
        parameters.put("endDate", new java.sql.Date(endDate.getTime()));
        return generateReportResponse("daterange", parameters);
    }
    //findBookByMovieId
    @GetMapping("/download/findBookBymovieId")//done
    public ResponseEntity<byte[]> downloadFindBookBymovieId(
            @RequestParam("movieId")Integer movieId)
    {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("movieId", movieId);
        return generateReportResponse("findBookByMovieId", parameters);
    }
    @GetMapping("/download/findBookStatusCountByUser")
    public ResponseEntity<byte[]> downloadFindBookStatusCountByUser(@RequestParam Integer paid) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("paid", paid);
        return generateReportResponse("findBookPaidCountByUser", parameters);
    }
    @GetMapping("/download/MovieOrderCount")
    public ResponseEntity<byte[]> downloadFindMovieOrderCount() {//done
        HashMap<String, Object> parameters = new HashMap<>();
        return generateReportResponse("findMovieOrderCount", parameters);
    }

    @GetMapping("/download/findMovieOrderStatusCount")//被取代了
    public ResponseEntity<byte[]> downloadFindMovieOrderStatusCount(@RequestParam Integer status) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("status", status);
        return generateReportResponse("findMovieOrderStatusCount", parameters);
    }
    @GetMapping("/download/findMovieOrderPaidCountTimeRange")
    public ResponseEntity<byte[]> downloadFindMovieOrderPaidCountTimeRange(//done
            @RequestParam Integer status,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate)
    {
        if (startDate == null) {
            startDate = LocalDate.of(1999, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("status", status);
        parameters.put("startDate", java.sql.Date.valueOf(startDate));
        parameters.put("endDate", java.sql.Date.valueOf(endDate));
        return generateReportResponse("findMovieOrderStatusCountTimeRange", parameters);
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
