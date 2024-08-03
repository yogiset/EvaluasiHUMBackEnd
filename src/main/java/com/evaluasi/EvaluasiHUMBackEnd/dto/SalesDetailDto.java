package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDetailDto {
    private Long id;
    private String bulan;
    private int targetblntotal;
    private int tercapaiitotal;
    private String tercapaipersenntotal;
    private int targetblngadus;
    private int tercapaiigadus;
    private String tercapaipersenngadus;
    private int targetblnpremium;
    private int tercapaiipremium;
    private String tercapaipersennpremium;
    private double jumlahvisit;
    private Long idsales;
}
