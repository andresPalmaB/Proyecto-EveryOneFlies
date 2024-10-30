package com.betek.ms_flies.dto.dtoModel;

import java.util.List;

public record FlightSeatSoldDTO(
        String flightCode,
        Integer seatsSoldEconomy,
        Integer seatsSoldPremiumEconomy,
        Integer seatsSoldBusiness,
        Integer seatsSoldFirstClass,
        List<String> seatCode
) {
}
