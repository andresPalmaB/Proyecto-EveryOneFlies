package com.betek.everyOneFlies.dto.dtoModel;

public record PassengerDTO (Boolean isResponsibleForPayment,
                            String firstName,
                            String lastName,
                            String email,
                            String phone){
}
