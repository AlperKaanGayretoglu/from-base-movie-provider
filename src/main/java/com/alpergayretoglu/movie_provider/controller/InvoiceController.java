package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.model.request.payment.PaymentRequest;
import com.alpergayretoglu.movie_provider.model.response.InvoiceResponse;
import com.alpergayretoglu.movie_provider.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping
    @ApiPageable
    public Page<InvoiceResponse> listInvoices(@ApiIgnore Pageable pageable) {
        return invoiceService.listInvoices(pageable);
    }

    @GetMapping("{id}")
    public InvoiceResponse getInvoice(@PathVariable String id) {
        return InvoiceResponse.fromEntity(invoiceService.getInvoice(id));
    }

    // POST /invoice/{id}/pay -> Invoice (owner of the invoice only)
    @PostMapping("{id}/pay")
    public InvoiceResponse payInvoice(@PathVariable String id,
                                      @Valid @RequestBody PaymentRequest paymentRequest) {
        return InvoiceResponse.fromEntity(invoiceService.payInvoice(id, paymentRequest));
    }
}