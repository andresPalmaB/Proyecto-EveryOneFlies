package com.betek.ms_pay.controller;

import com.betek.ms_pay.models.PaymentRequest;
import com.betek.ms_pay.models.ReserveDTO;
import com.betek.ms_pay.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

//validacion
    @GetMapping("/validacion")
    public ResponseEntity<ReserveDTO> validarPago (@RequestBody ReserveDTO reserveDTO){

            return ResponseEntity.ok(paymentService.ValidationReserve(reserveDTO));
    }

    // Endpoint para crear el pago
    @PostMapping("/create")
    public ResponseEntity<Object> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentRequest);
            return ResponseEntity.ok(new PaymentResponse(paymentIntent.getId(), paymentIntent.getStatus()));
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error al crear el pago: " + e.getMessage());
        }
    }

    // Endpoint para recuperar un pago por ID
    @GetMapping("/retrieve/{paymentId}")
    public ResponseEntity<Object> retrievePayment(@PathVariable String paymentId) {
        try {
            PaymentIntent paymentIntent = paymentService.retrievePayment(paymentId);
            return ResponseEntity.ok(new PaymentResponse(paymentIntent.getId(), paymentIntent.getStatus()));
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error al recuperar el pago: " + e.getMessage());
        }
    }

    // Endpoint para cancelar un pago por ID
    @PostMapping("/cancel/{paymentId}")
    public ResponseEntity<Object> cancelPayment(@PathVariable String paymentId) {
        try {
            PaymentIntent paymentIntent = paymentService.cancelPayment(paymentId);
            return ResponseEntity.ok(new PaymentResponse(paymentIntent.getId(), paymentIntent.getStatus()));
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error al cancelar el pago: " + e.getMessage());
        }
    }

    // Endpoint para reembolsar un pago por ID
    @PostMapping("/refund/{paymentId}")
    public ResponseEntity<Object> refundPayment(@PathVariable String paymentId) {
        try {
            PaymentIntent paymentIntent = paymentService.refundPayment(paymentId);
            return ResponseEntity.ok(new PaymentResponse(paymentIntent.getId(), paymentIntent.getStatus()));
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error al reembolsar el pago: " + e.getMessage());
        }
    }
}

// Clase de respuesta para simplificar la información enviada al cliente
class PaymentResponse {
    private String transactionId;
    private String status;

    public PaymentResponse(String transactionId, String status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    // Getters y Setters
    public String getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }
}
