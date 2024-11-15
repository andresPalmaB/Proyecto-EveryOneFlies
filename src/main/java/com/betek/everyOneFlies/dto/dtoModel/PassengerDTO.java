package com.betek.everyOneFlies.dto.dtoModel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PassengerDTO (@NotNull(message = "is mandatory")
                            Boolean isResponsibleForPayment,

                            @NotBlank(message = "is mandatory")
                            String firstName,

                            @NotBlank(message = "is mandatory")
                            String lastName,

                            @NotBlank(message = "is mandatory")
                            @Email(message = "does not have a valid format")
                            String email,

                            @NotBlank(message = "is mandatory")
                            @Pattern(
                                    regexp = "^\\+\\d{1,3}-\\d{7,15}$",
                                    message = "must have the format +123-xxxxxxxx (country code of up to three digits followed by a number of 7 to 15 digits)"
                            )
                            String phone){
}
