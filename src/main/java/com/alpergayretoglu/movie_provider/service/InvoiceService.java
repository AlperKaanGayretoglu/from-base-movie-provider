package com.alpergayretoglu.movie_provider.service;

import com.alpergayretoglu.movie_provider.exception.EntityNotFoundException;
import com.alpergayretoglu.movie_provider.model.entity.ContractRecord;
import com.alpergayretoglu.movie_provider.model.entity.Invoice;
import com.alpergayretoglu.movie_provider.model.entity.Payment;
import com.alpergayretoglu.movie_provider.model.request.payment.PaymentRequest;
import com.alpergayretoglu.movie_provider.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository repository;
    private final PaymentService paymentService;

    public List<Invoice> listInvoices() {
        return repository.findAll();
    }

    public Page<Invoice> listInvoices(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public void createInvoice(ContractRecord contractRecord) {
        int duration = contractRecord.getDuration(); // remaining invoices // TODO: Isn't this about duration?
        if (duration < 1) return; // TODO : throw exception?
        contractRecord.setDuration(duration - 1); // decrease the duration // TODO: Why are we doing this?
        // TODO : no need to contractRepository save?

        repository.save(Invoice.builder()
                .fee(contractRecord.getMonthlyFee())
                .contractRecord(contractRecord)
                .build());
    }

    public Invoice getInvoice(String id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    @Transactional
    public Invoice payInvoice(String invoiceId, PaymentRequest paymentRequest) {
        Invoice invoice = findById(invoiceId);
        int amount = paymentRequest.getAmount();

        // validations, TODO : prettify later, custom annotation based validations
        if (amount < 1) throw new IllegalStateException("amount cannot be less than 1");
        else if (amount > invoice.getFee()) {
            throw new IllegalStateException("amount cannot be greater than invoice fee");
        }

        invoice.setFee(invoice.getFee() - amount);
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
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}