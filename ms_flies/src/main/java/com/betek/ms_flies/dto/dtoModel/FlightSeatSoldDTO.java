package com.betek.ms_flies.dto.dtoModel;

public record FlightSeatSoldDTO(
        String flightCode,
        Integer seatsSoldEconomy,
        Integer seatsSoldPremiumEconomy,
        Integer seatsSoldBusiness,
        Integer seatsSoldFirstClass
) {
}
