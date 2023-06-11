package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.EntityNotFoundException;
import com.alpergayretoglu.movie_provider.model.entity.ContractRecord;
import com.alpergayretoglu.movie_provider.model.entity.Invoice;
import com.alpergayretoglu.movie_provider.model.entity.Payment;
import com.alpergayretoglu.movie_provider.model.request.payment.PaymentRequest;
import com.alpergayretoglu.movie_provider.model.response.InvoiceResponse;
import com.alpergayretoglu.movie_provider.repository.ContractRecordRepository;
import com.alpergayretoglu.movie_provider.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ContractRecordRepository contractRecordRepository;
    private final PaymentService paymentService;

    public Page<Invoice> listInvoices(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public void createInvoice(ContractRecord contractRecord) {
        if (contractRecord.getRemainingDuration() <= 0) {
            throw new IllegalStateException("Contract record has no remaining duration");
        }

        invoiceRepository.save(Invoice.builder()
                .fee(contractRecord.getMonthlyFee())
                .contractRecord(contractRecordRepository.save(contractRecord))
                .build());
    }

    public Invoice getInvoice(String id) {
        return invoiceRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Invoice payInvoice(String invoiceId, PaymentRequest paymentRequest) {
        Invoice invoice = findById(invoiceId);

        invoice.pay(paymentRequest.getAmount());

        Payment payment = Payment.builder()
                .amount(paymentRequest.getAmount())
                .invoice(invoice)
                .senderCard(paymentRequest.getSenderCard())
                .receiverCard(paymentRequest.getReceiverCard())
                .build();

        paymentService.addPayment(payment);
        return invoice;
    }

    public Invoice findById(String id) {
        return invoiceRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Page<InvoiceResponse> listInvoicesForContractRecords(List<ContractRecord> contractRecords, Pageable pageable) {
        return invoiceRepository.findAllByContractRecordIn(contractRecords, pageable).map(InvoiceResponse::fromEntity);
    }
}