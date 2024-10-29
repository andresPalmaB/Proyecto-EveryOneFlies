package com.betek.ms_pay.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
//uso loombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private int amount;//monto
    private String currency;//moneda
    private String description;//descripcion del pago
    private String paymentMethod;//metodo de pago
    private PaymentStatus paymentStatus; //estado del pago
    private LocalDateTime paymentDate;//fecha del pago
    private String transactionId;//id del pago
    private String payerEmail; //correo del pagador

}
