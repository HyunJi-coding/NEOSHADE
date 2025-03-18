package org.example.login.controller;

import org.example.login.dto.Request.OrderRequest;
import org.example.login.dto.Request.PaymentsRequest;
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
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("ss_member_id");

        if (userId == null) {
            return ResponseEntity.status(401).body("{\"success\": false, \"error\": \"User not logged in\"}");
        }

        if (orderRequest.getImpUid() == null || orderRequest.getImpUid().isEmpty()) {
            return ResponseEntity.badRequest().body("{\"success\": false, \"error\": \"imp_uid is missing\"}");
        }

        try {
            boolean isVerified = paymentService.verifyPayment(orderRequest.getImpUid(), orderRequest.getMerchantUid());
            if (!isVerified) {
                return ResponseEntity.badRequest().body("{\"success\": false, \"error\": \"Payment verification failed\"}");
            }

            PaymentsRequest paymentRequest = PaymentsRequest.builder()
                    .paymentAmount(orderRequest.getTotal())
                    .build();

            Orders order = orderService.processOrder(orderRequest, paymentRequest, userId); // PaymentRequest 매개변수 추가
            return ResponseEntity.ok().body("{\"success\": true, \"order\": " + order + "}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }

}