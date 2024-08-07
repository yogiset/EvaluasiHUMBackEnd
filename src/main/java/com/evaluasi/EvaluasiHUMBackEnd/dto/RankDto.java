package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankDto {
    private Long idsales;
    private String nama;
    private int tahun;
    private double achivementtotal;
    private double achivementgadus;
    private double achivementpremium;
    private double jumcustomer;
    private double jumvisit;
    private double hasil;
    private Integer rank;
}
