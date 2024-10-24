package com.betek.ms_flies.dto.dtoModel;

public record VueloAsientoVendidoDTO(
        String codigo,
        Integer asientoVendidosEconomicos,
        Integer asientoVendidosEconomicosPremiun,
        Integer asientoVendidosBusiness,
        Integer asientoVendidosPrimeraClase
) {
}
