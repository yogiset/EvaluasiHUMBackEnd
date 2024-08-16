package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RankBulanDto {
    private Long idsales;
    private Long id;
    private String nama;
    private int tahun;
    private String bulan;
    private double achivementtotal;
    private double achivementgadus;
    private double achivementpremium;
    private double jumcustomer;
    private double jumvisit;
    private double hasil;
    private Integer rank;
}
