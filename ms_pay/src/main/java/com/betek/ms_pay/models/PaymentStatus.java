package com.betek.ms_pay.models;

public enum PaymentStatus {
    SUCCESS,  // Pago exitoso
    FAILED,   // Pago fallido
    PENDING,  // Pago pendiente
    REFUNDED, // Pago reembolsado
    CANCELED  // Pago cancelado
}
