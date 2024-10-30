package com.betek.ms_flies.service.flightService;

import com.betek.ms_flies.model.Flight;
import com.betek.ms_flies.model.Location;
import com.betek.ms_flies.model.modelEnum.TipoAsiento;

import java.time.LocalDate;
import java.util.Comparator;

public final class FlightCalculateCost {

    public static Double Calculate(TipoAsiento tipo, Location city, LocalDate date, Flight flight){
        switch (tipo){
            case ECONOMYC -> {
                return city.getBasePrice() * 0.85 *
                        CalculatePriceBySeason(city, date) *
                        CalculatePriceByDemand(flight);
            }
            case ECONOMYCPREMIUM -> {
                return city.getBasePrice() * 1.1 *
                        CalculatePriceBySeason(city, flight.getDepartureDate()) *
                        CalculatePriceByDemand(flight);
            }
            case BUSINESS -> {
                return city.getBasePrice() * 1.8 *
                        CalculatePriceBySeason(city, date) *
                        CalculatePriceByDemand(flight);
            }
            case FIRST_CLASS -> {
                return city.getBasePrice() * 2.3 *
                        CalculatePriceBySeason(city, date) *
                        CalculatePriceByDemand(flight);
            }
        }
        return null;
    }

    private static Double CalculatePriceBySeason(Location ciudad, LocalDate fecha){

        Comparator<LocalDate> compararDiaMes = Comparator.comparing(LocalDate::getMonth)
                .thenComparing(LocalDate::getDayOfMonth);

        boolean estaDentroRangoAlto = compararDiaMes.compare(fecha, ciudad.getHighSeasonStartDate()) >= 0 &&
                compararDiaMes.compare(fecha, ciudad.getHighSeasonEndDate()) <= 0 ||
                compararDiaMes.compare(fecha, ciudad.getHighSeasonStartDate2()) >= 0 &&
                        compararDiaMes.compare(fecha, ciudad.getHighSeasonEndDate2()) <= 0;

        boolean estaDentroRangoMedia = compararDiaMes.compare(fecha, ciudad.getMidSeasonStartDate()) >= 0 &&
                compararDiaMes.compare(fecha, ciudad.getMidSeasonEndDate()) <= 0 ||
                compararDiaMes.compare(fecha, ciudad.getMidSeasonStartDate2()) >= 0 &&
                        compararDiaMes.compare(fecha, ciudad.getMidSeasonEndDate2()) <= 0;

        if (estaDentroRangoAlto){
            return 1.5;
        } else if (estaDentroRangoMedia){
            return 1.2;
        }
        return 0.9;

    }

    private static double CalculatePriceByDemand(Flight flight) {

        int totalPuestosVendidos = flight.getBusinessCounter() + flight.getEconomyCounter() +
                flight.getPremiumEconomyCounter() + flight.getFirstClassCounter();

        if (totalPuestosVendidos > 0.5 * flight.getTotalSeats() &&
                totalPuestosVendidos < 0.8 * flight.getTotalSeats()){
            return 1.1;
        } else if (totalPuestosVendidos > 0.8 * flight.getTotalSeats()){
            return 1.2;
        }
        return 1;

    }

}
