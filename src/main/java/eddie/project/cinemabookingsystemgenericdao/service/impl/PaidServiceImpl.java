package eddie.project.cinemabookingsystemgenericdao.service.impl;

import eddie.project.cinemabookingsystemgenericdao.dao.BookDao;
import eddie.project.cinemabookingsystemgenericdao.dto.book.BookStatusUpdate;
import eddie.project.cinemabookingsystemgenericdao.entity.Book;
import eddie.project.cinemabookingsystemgenericdao.exception.CustomNotFoundException;
import eddie.project.cinemabookingsystemgenericdao.exception.UnauthorizedException;
import eddie.project.cinemabookingsystemgenericdao.service.BookService;
import eddie.project.cinemabookingsystemgenericdao.service.PaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class PaidServiceImpl implements PaidService {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDao bookDao;

    /**
     * 建立支付請求
     */
    @Override
    public String createPayment(Integer uid, BookStatusUpdate bookStatusUpdate) {
        // 查詢訂單
        Book book = bookDao.findById(bookStatusUpdate.getId());
        if (book == null) {
            throw new CustomNotFoundException("找不到訂單 ID: " + bookStatusUpdate.getId());
        }
        if (!book.getUserId().equals(uid)) {
            throw new UnauthorizedException("你沒有權限修改此訂單");
        }

        // 產生支付連結（模擬支付）
        return "http://localhost:8080/fake-payment?orderId=" + bookStatusUpdate.getId();
    }

    /**
     * 處理支付回調
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processPaymentCallback(Map<String, String> payload) {
        Integer orderId = Integer.parseInt(payload.get("orderId"));
        boolean success = "success".equals(payload.get("status"));

        BookStatusUpdate bookStatusUpdate = new BookStatusUpdate();
        bookStatusUpdate.setId(orderId);
        bookStatusUpdate.setStatus(success ? 1 : 2);

        // 調用 BookService 更新訂單狀態
        bookService.statusUpdate(bookDao.findById(orderId).getUserId(), bookStatusUpdate);
    }

    /**
     * 查詢支付狀態
     */
    @Override
    public String getPaymentStatus(Integer orderId) {
        Book book = bookDao.findById(orderId);
        if (book == null) {
            throw new CustomNotFoundException("找不到訂單 ID: " + orderId);
        }

        return switch (book.getStatus()) {
            case 1 -> "已付款，付款時間：" + book.getPayTime();
            case 2 -> "已取消";
            default -> "未付款";
        };
    }
}
