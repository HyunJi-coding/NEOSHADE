package org.example.login.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final IamportClient iamportClient;

    @Autowired
    public PaymentService(IamportClient iamportClient) {
        this.iamportClient = iamportClient;
    }

    public boolean verifyPayment(String impUid, String merchantUid) throws Exception {
        IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(impUid);

        if (paymentResponse.getResponse() != null) {
            Payment payment = paymentResponse.getResponse();
            return payment.getMerchantUid().equals(merchantUid);
        }

        return false;
    }
}