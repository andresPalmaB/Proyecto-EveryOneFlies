package com.betek.everyOneFlies.service.flightService;

import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Location;
import com.betek.everyOneFlies.model.modelEnum.SeatCategory;

import java.time.LocalDate;
import java.time.MonthDay;

public final class FlightCalculateCost {

    public static Double Calculate(SeatCategory category, Location city, LocalDate date, Flight flight){
        switch (category){
            case ECONOMICAL -> {
                double proportionSeason = calculatePriceBySeason(city, date);
                double proportionDemand = calculatePriceByDemand(flight);
                return city.getBasePrice() * 0.85 * proportionSeason * proportionDemand;
            }
            case ECONOMICAL_PREMIUM -> {
                double proportionSeason = calculatePriceBySeason(city, date);
                double proportionDemand = calculatePriceByDemand(flight);
                return city.getBasePrice() * 1.1 * proportionSeason * proportionDemand;
            }
            case BUSINESS -> {
                double proportionSeason = calculatePriceBySeason(city, date);
                double proportionDemand = calculatePriceByDemand(flight);
                return city.getBasePrice() * 1.8 * proportionSeason * proportionDemand;
            }
            case FIRST_CLASS -> {
                double proportionSeason = calculatePriceBySeason(city, date);
                double proportionDemand = calculatePriceByDemand(flight);
                return city.getBasePrice() * 2.3 * proportionSeason * proportionDemand;
            }
        }
        return null;
    }

    private static Double calculatePriceBySeason(Location city, LocalDate date) {
        MonthDay targetDate = MonthDay.of(date.getMonth(), date.getDayOfMonth());

        if (isInSeason(city.getStartDateH(), city.getEndDateH(),
                city.getStartDateH2(), city.getEndDateH2(), targetDate)) {
            return 1.5;
        } else if (isInSeason(city.getStartDateM(), city.getEndDateM(),
                city.getStartDateM2(), city.getEndDateM2(), targetDate)) {
            return 1.2;
        }
        return 0.9;
    }

    private static boolean isInSeason(LocalDate startDate1, LocalDate endDate1,
                                      LocalDate startDate2, LocalDate endDate2,
                                      MonthDay targetDate) {
        MonthDay start1 = MonthDay.of(startDate1.getMonth(), startDate1.getDayOfMonth());
        MonthDay end1 = MonthDay.of(endDate1.getMonth(), endDate1.getDayOfMonth());
        MonthDay start2 = MonthDay.of(startDate2.getMonth(), startDate2.getDayOfMonth());
        MonthDay end2 = MonthDay.of(endDate2.getMonth(), endDate2.getDayOfMonth());

        return isWithinRange(start1, end1, targetDate) || isWithinRange(start2, end2, targetDate);
    }

    private static boolean isWithinRange(MonthDay start, MonthDay end, MonthDay target) {
        if (end.isAfter(start) || end.equals(start)) {
            return !target.isBefore(start) || !target.isAfter(end);
        } else {
            return target.isAfter(start) && target.isBefore(end);
        }
    }

    private static double calculatePriceByDemand(Flight flight) {

        int totalSeatsSold = flight.getTotalSeats() - (flight.getBusinessCounter() + flight.getEconomyCounter() +
                flight.getPremiumEconomyCounter() + flight.getFirstClassCounter());

        if (totalSeatsSold > 0.5 * flight.getTotalSeats() &&
                totalSeatsSold < 0.8 * flight.getTotalSeats()){
            return 1.1;
        } else if (totalSeatsSold > 0.8 * flight.getTotalSeats()){
            return 1.2;
        }
        return 1;

    }

}
