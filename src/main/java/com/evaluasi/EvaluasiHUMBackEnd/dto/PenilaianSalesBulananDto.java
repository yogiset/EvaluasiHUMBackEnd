package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PenilaianSalesBulananDto {
    private Long idsales;
    private Long id;
    private String nama;
    private Integer tahun;
    private String bulan;
    private double achievtotal;
    private double achievgadus;
    private double achievpremium;
    private double jumcustomer;
    private double jumvisit;
}
