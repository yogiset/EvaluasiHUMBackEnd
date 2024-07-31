package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PenilaianSalesDto {
    private Long idsales;
    private double achievtotal;
    private double achievgadus;
    private double achievpremium;
    private double jumcustomer;
    private double jumvisit;
    private String nama;
    private Integer tahun;
}
