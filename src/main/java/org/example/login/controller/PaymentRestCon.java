package org.example.login.controller;

import org.example.login.dto.OrderRequest;
import org.example.login.entity.Orders;
import org.example.login.service.OrdersService;
import org.example.login.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/payments")
public class PaymentRestCon {

    @Autowired
    OrdersService orderService;
    @Autowired
    PaymentService paymentService;



    @PostMapping("/complete")
    public ResponseEntity<?> completePayment(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        System.out.println("Received /complete request"); // 로그 추가

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            System.out.println("User not logged in"); // 로그 추가
            return ResponseEntity.status(401).body("{\"success\": false, \"error\": \"User not logged in\"}");
        }

        // 요청 데이터 로그 출력
        System.out.println("OrderRequest: " + orderRequest);

        if (orderRequest.getImpUid() == null || orderRequest.getImpUid().isEmpty()) {
            System.out.println("imp_uid is missing"); // 로그 추가
            return ResponseEntity.badRequest().body("{\"success\": false, \"error\": \"imp_uid is missing\"}");
        }

        try {
            // 결제 검증
            boolean isVerified = paymentService.verifyPayment(orderRequest.getImpUid(), orderRequest.getMerchantUid());
            if (!isVerified) {
                System.out.println("Payment verification failed"); // 로그 추가
                return ResponseEntity.badRequest().body("{\"success\": false, \"error\": \"Payment verification failed\"}");
            }

            // 주문 처리
            Orders order = orderService.processOrder(orderRequest, userId);
            System.out.println("Order processed: " + order); // 로그 추가
            return ResponseEntity.ok().body("{\"success\": true, \"order\": " + order + "}");
        } catch (Exception e) {
            e.printStackTrace();  // 예외 출력
            return ResponseEntity.status(500).body("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }

}