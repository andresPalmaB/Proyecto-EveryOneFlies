package com.betek.everyOneFlies.service.flightService;

import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Location;
import com.betek.everyOneFlies.model.modelEnum.SeatCategory;

import java.time.LocalDate;
import java.time.MonthDay;

public final class FlightCalculateCost {

    public static Double Calculate(SeatCategory tipo, Location city, LocalDate date, Flight flight){
        switch (tipo){
            case ECONOMICAL -> {
                double proporcionSeason = CalculatePriceBySeason(city, date);
                double proporcionDemand = CalculatePriceByDemand(flight);
                return city.getBasePrice() * 0.85 * proporcionSeason * proporcionDemand;
            }
            case ECONOMICAL_PREMIUM -> {
                double proporcionSeason = CalculatePriceBySeason(city, date);
                double proporcionDemand = CalculatePriceByDemand(flight);
                return city.getBasePrice() * 1.1 * proporcionSeason * proporcionDemand;
            }
            case BUSINESS -> {
                double proporcionSeason = CalculatePriceBySeason(city, date);
                double proporcionDemand = CalculatePriceByDemand(flight);
                return city.getBasePrice() * 1.8 * proporcionSeason * proporcionDemand;
            }
            case FIRST_CLASS -> {
                double proporcionSeason = CalculatePriceBySeason(city, date);
                double proporcionDemand = CalculatePriceByDemand(flight);
                return city.getBasePrice() * 2.3 * proporcionSeason * proporcionDemand;
            }
        }
        return null;
    }

    private static Double CalculatePriceBySeason(Location ciudad, LocalDate fecha){

        MonthDay startHigh1 = MonthDay.of(
                ciudad.getHighSeasonStartDate().getMonth(),ciudad.getHighSeasonStartDate().getDayOfMonth()
        );

        MonthDay endHigh1 = MonthDay.of(
                ciudad.getHighSeasonEndDate().getMonth(), ciudad.getHighSeasonEndDate().getDayOfMonth()
        );

        MonthDay startHigh2 = MonthDay.of(
                ciudad.getHighSeasonStartDate2().getMonth(),ciudad.getHighSeasonStartDate2().getDayOfMonth()
        );

        MonthDay endHigh2 = MonthDay.of(
                ciudad.getHighSeasonEndDate2().getMonth(), ciudad.getHighSeasonEndDate2().getDayOfMonth()
        );

        MonthDay target = MonthDay.of(fecha.getMonth(), fecha.getDayOfMonth());

        boolean estaDentroRangoAlto = target.isAfter(startHigh1) || target.isBefore(endHigh1) ||
                target.isAfter(startHigh2) && target.isBefore(endHigh2);

        MonthDay startM1 = MonthDay.of(
                ciudad.getMidSeasonStartDate().getMonth(),ciudad.getMidSeasonStartDate().getDayOfMonth()
        );

        MonthDay endM1 = MonthDay.of(
                ciudad.getMidSeasonEndDate().getMonth(), ciudad.getMidSeasonEndDate().getDayOfMonth()
        );

        MonthDay startM2 = MonthDay.of(
                ciudad.getMidSeasonStartDate2().getMonth(),ciudad.getMidSeasonStartDate2().getDayOfMonth()
        );

        MonthDay endM2 = MonthDay.of(
                ciudad.getMidSeasonEndDate2().getMonth(), ciudad.getMidSeasonEndDate2().getDayOfMonth()
        );

        MonthDay target2 = MonthDay.of(fecha.getMonth(), fecha.getDayOfMonth());

        boolean estaDentroRangoMedia = target2.isAfter(startM1) && target2.isBefore(endM1) ||
                target2.isAfter(startM2) && target2.isBefore(endM2);

        if (estaDentroRangoAlto){
            return 1.5;
        } else if (estaDentroRangoMedia){
            return 1.2;
        }
        return 0.9;

    }

    private static double CalculatePriceByDemand(Flight flight) {

        int totalPuestosVendidos = flight.getTotalSeats() - (flight.getBusinessCounter() + flight.getEconomyCounter() +
                flight.getPremiumEconomyCounter() + flight.getFirstClassCounter());

        if (totalPuestosVendidos > 0.5 * flight.getTotalSeats() &&
                totalPuestosVendidos < 0.8 * flight.getTotalSeats()){
            return 1.1;
        } else if (totalPuestosVendidos > 0.8 * flight.getTotalSeats()){
            return 1.2;
        }
        return 1;

    }

}
