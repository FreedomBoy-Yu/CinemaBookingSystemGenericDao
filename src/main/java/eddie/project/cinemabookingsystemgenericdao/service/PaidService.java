package eddie.project.cinemabookingsystemgenericdao.service;

import eddie.project.cinemabookingsystemgenericdao.dto.book.BookStatusUpdate;

import java.util.Map;

public interface PaidService {

    /**
     * 建立支付請求，返回支付連結
     * @param uid 用戶 ID
     * @param bookStatusUpdate 訂單更新請求
     * @return 支付連結
     */
    String createPayment(Integer uid, BookStatusUpdate bookStatusUpdate);

    /**
     * 處理支付成功或失敗的回調
     * @param payload 支付回應數據
     */
    void processPaymentCallback(Map<String, String> payload);

    /**
     * 查詢訂單的支付狀態
     * @param orderId 訂單 ID
     * @return 訂單狀態資訊
     */
    String getPaymentStatus(Integer orderId);
}
