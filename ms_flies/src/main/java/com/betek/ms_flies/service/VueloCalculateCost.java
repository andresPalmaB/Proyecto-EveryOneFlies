package com.betek.ms_flies.service;

import com.betek.ms_flies.model.Location;
import com.betek.ms_flies.model.Vuelo;
import com.betek.ms_flies.model.modelEnum.TipoAsiento;

import java.time.LocalDate;
import java.util.Comparator;

public final class VueloCalculateCost {

    public static Double Calculate(TipoAsiento tipo, Location ciudad, LocalDate fecha, Vuelo vuelo){
        switch (tipo){
            case ECONOMICO -> {
                return ciudad.getPrecioBase() * 0.85 *
                       CalculatePriceBySeason(ciudad, fecha) *
                       CalculatePriceByDemand(vuelo);
            }
            case ECONOMICOPREMIUN -> {
                return ciudad.getPrecioBase() * 1.1 *
                       CalculatePriceBySeason(ciudad, fecha) *
                       CalculatePriceByDemand(vuelo);
            }
            case BUSINESS -> {
                return ciudad.getPrecioBase() * 1.8 *
                       CalculatePriceBySeason(ciudad, fecha) *
                       CalculatePriceByDemand(vuelo);
            }
            case PRIMERACLASE -> {
                return ciudad.getPrecioBase() * 2.3 *
                       CalculatePriceBySeason(ciudad, fecha) *
                       CalculatePriceByDemand(vuelo);
            }
        }
        return null;
    }

    private static Double CalculatePriceBySeason(Location ciudad, LocalDate fecha){

        Comparator<LocalDate> compararDiaMes = Comparator.comparing(LocalDate::getMonth)
                .thenComparing(LocalDate::getDayOfMonth);

        boolean estaDentroRangoAlto = compararDiaMes.compare(fecha, ciudad.getFechaInicioAlta()) >= 0 &&
                                    compararDiaMes.compare(fecha, ciudad.getFechaFinAlta()) <= 0;

        boolean estaDentroRangoMedia = compararDiaMes.compare(fecha, ciudad.getFechaInicioMedia()) >= 0 &&
                                      compararDiaMes.compare(fecha, ciudad.getFechaFinMedia()) <= 0 ||
                                      compararDiaMes.compare(fecha, ciudad.getFechaInicioMedia2()) >= 0 &&
                                      compararDiaMes.compare(fecha, ciudad.getFechaFinMedia2()) <= 0;

        if (estaDentroRangoAlto){
            return 1.5;
        } else if (estaDentroRangoMedia){
            return 1.2;
        }
        return 0.9;

    }

    private static double CalculatePriceByDemand(Vuelo vuelo) {

        int totalPuestosVendidos = vuelo.getContadorBusiness() + vuelo.getContadorEconomicos() +
                                   vuelo.getContadorEconomicosPremiun() + vuelo.getContadorPrimeraClase();

        if (totalPuestosVendidos > 0.5 * vuelo.getTotalPuestos() &&
            totalPuestosVendidos < 0.8 * vuelo.getTotalPuestos()){
            return 1.1;
        } else if (totalPuestosVendidos > 0.8 * vuelo.getTotalPuestos()){
            return 1.2;
        }
        return 1;

    }

}
