package com.fpoly.service;

import com.fpoly.dto.request.InvoiceRequest;
import com.fpoly.dto.request.InvoiceRequestCounter;
import com.fpoly.dto.response.InvoiceResponse;
import com.fpoly.enums.PayType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public interface InvoiceService {
    
    public InvoiceResponse create(InvoiceRequest invoiceRequest);

    public InvoiceResponse updateStatus(Long invoiceId, Long statusId);

    public List<InvoiceResponse> findByUser();

    public Page<InvoiceResponse> findAll(Date from, Date to, Pageable pageable);

    public InvoiceResponse cancelInvoice(Long invoiceId);

    public InvoiceResponse findById(Long invoiceId);

    public InvoiceResponse findByIdForAdmin(Long invoiceId);

    public Page<InvoiceResponse> findAllFull(Date from, Date to, PayType payType, Long statusId, Pageable pageable);

    void payCounter(InvoiceRequestCounter invoiceRequestCounter);
}