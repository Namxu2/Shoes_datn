package com.fpoly.api;

import com.fpoly.dto.request.PaymentDto;
import com.fpoly.dto.request.ProductSizeRequest;
import com.fpoly.dto.response.ResponsePayment;
import com.fpoly.entity.ProductSize;
import com.fpoly.entity.User;
import com.fpoly.entity.Voucher;
import com.fpoly.exception.MessageException;
import com.fpoly.gpay.dto.GpayPaymentRequest;
import com.fpoly.gpay.service.GpayService;
import com.fpoly.repository.ProductSizeRepository;
import com.fpoly.servive.VoucherService;
import com.fpoly.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/gpay")
@CrossOrigin
public class GpayApi {

    @Autowired
    private GpayService gpayService;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private UserUtils userUtils;

    @PostMapping("/urlpayment")
    public ResponsePayment getUrlPayment(@RequestBody PaymentDto paymentDto) {
        String orderId = String.valueOf(System.currentTimeMillis());
        Double totalAmount = 0D;
        for(ProductSizeRequest p : paymentDto.getListProductSize()){
            if(p.getIdProductSize() == null){
                throw new MessageException("id product size require");
            }
            Optional<ProductSize> productSize = productSizeRepository.findById(p.getIdProductSize());
            if(productSize.isEmpty()){
                throw new MessageException("product size: "+p.getIdProductSize()+" not found");
            }
            if(productSize.get().getQuantity() < p.getQuantity()){
                throw new MessageException("product size: "+p.getIdProductSize()+" not enough quantity");
            }
            totalAmount += productSize.get().getProductColor().getProduct().getPrice() * p.getQuantity();
        }
        if(paymentDto.getCodeVoucher() != null){
            Optional<Voucher> voucher = voucherService.findByCode(paymentDto.getCodeVoucher(), totalAmount);
            if(voucher.isPresent()){
                totalAmount = totalAmount - voucher.get().getDiscount();
            }
        }
        Long td = Math.round(totalAmount);
        System.out.println("tong tien: "+td);
        User user = userUtils.getUserWithAuthority();
        GpayPaymentRequest gpayPaymentRequest = new GpayPaymentRequest(orderId, td.longValue(), "Thanh toán gpay","",user.getId().toString(),
                user.getFullname(), "0423534562",user.getEmail(), "Viet Nam","VA","{}","IMMEDIATE");

        String paymentUrl = gpayService.createPaymentUrl(gpayPaymentRequest);
        ResponsePayment responsePayment = new ResponsePayment(paymentUrl,orderId,orderId);
        return responsePayment;
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam Map<String, String> params, Model model) {
        model.addAttribute("params", params);
        return "payment/result";
    }

    @PostMapping("/webhook")
    @ResponseBody
    public ResponseEntity<String> webhook(@RequestBody Map<String, Object> payload) {
        boolean isValid = gpayService.verifyWebhookSignature(payload);
        if (isValid) {
            gpayService.processPaymentNotification(payload);
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.badRequest().body("Invalid signature");
        }
    }


}