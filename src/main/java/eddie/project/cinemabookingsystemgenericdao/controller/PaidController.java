package eddie.project.cinemabookingsystemgenericdao.controller;

import eddie.project.cinemabookingsystemgenericdao.dto.book.BookStatusUpdate;
import eddie.project.cinemabookingsystemgenericdao.service.PaidService;
import eddie.project.cinemabookingsystemgenericdao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pay")
@CrossOrigin(origins = "http://localhost:5173")
public class PaidController {

    @Autowired
    private PaidService paidService;

    @Autowired
    private UserService userService;

    // 產生支付請求
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPayment(@RequestHeader("Authorization") String token,
                                                             @RequestBody BookStatusUpdate request) {
        Integer userId = userService.jwtToUserId(token.replace("Bearer ", "")); // 解析 JWT
        String paymentUrl = paidService.createPayment(userId, request);
        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        return ResponseEntity.ok(response);
    }

    // 付款成功後的 Webhook 回調
    @PostMapping("/callback")
    public ResponseEntity<String> handlePaymentCallback(@RequestBody Map<String, String> payload) {
        paidService.processPaymentCallback(payload);
        return ResponseEntity.ok("Payment Processed");
    }

    // 查詢訂單支付狀態
    @GetMapping("/status/{orderId}")
    public ResponseEntity<String> checkPaymentStatus(@PathVariable Integer orderId) {
        String status = paidService.getPaymentStatus(orderId);
        return ResponseEntity.ok(status);
    }
    @GetMapping("/fake-payment")//模擬的支付外部連結
    public ResponseEntity<String> fakePayment(@RequestParam Integer orderId) {
        // 這裡可以呼叫 `paidService.processPaymentCallback(...)` 來模擬 Webhook
        Map<String, String> fakeCallbackData = new HashMap<>();
        fakeCallbackData.put("orderId", orderId.toString());
        fakeCallbackData.put("status", "success");
        fakeCallbackData.put("transactionId", "FAKE123456");

        paidService.processPaymentCallback(fakeCallbackData); // 手動呼叫支付回調

        return ResponseEntity.ok("模擬支付成功，訂單 " + orderId + " 已更新");
    }

}

