package com.betek.ms_pay.service;

import com.betek.ms_pay.exceptions.ErrorMassage;
import com.betek.ms_pay.models.PaymentRequest;
import com.betek.ms_pay.models.PaymentStatus;
import com.betek.ms_pay.models.ReservationStatus;
import com.betek.ms_pay.models.ReserveDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

     // Inyectamos la clave desde el archivo application.properties o application.yml
    @Value("${stripe.api.key}")
    private String apiKey;

    // Constructor del servicio de pagos
    public PaymentService() {
        // Inicializa la API de Stripe con la clave
        Stripe.apiKey = apiKey;
    }

    public ReserveDTO ValidationReserve(ReserveDTO reserveDTO){

        switch (reserveDTO.status()){
            case CONFIRMED, CANCELLED,REFUNDED,ON_HOLD,NO_SHOW -> {
                throw new ErrorMassage("Estado de la transacción " + reserveDTO.status() + ": No procede el pago");
            }

        }

        return  new ReserveDTO(ReservationStatus.PAID);



    }

    // Método para crear el pago
    public PaymentIntent createPaymentIntent(PaymentRequest paymentRequest) throws StripeException {
        // Parámetros que se envían a Stripe para crear el intento de pago
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentRequest.getAmount()); // Cantidad en centavos
        params.put("currency", paymentRequest.getCurrency()); // Moneda (ej: "usd")
        params.put("description", paymentRequest.getDescription());

        // Puedes cambiar esto para aceptar otros métodos de pago si es necesario
        params.put("payment_method_types", new String[]{"card"}); // Métodos de pago aceptados

        // Crea el PaymentIntent
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Actualiza el PaymentRequest con la información adicional
        paymentRequest.setTransactionId(paymentIntent.getId());
        paymentRequest.setPaymentDate(LocalDateTime.now()); // Establece la fecha del pago
        paymentRequest.setPaymentStatus(PaymentStatus.PENDING); // Establece el estado del pago

        return paymentIntent;
    }


}
