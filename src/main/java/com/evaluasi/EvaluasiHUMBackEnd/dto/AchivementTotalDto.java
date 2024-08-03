package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchivementTotalDto {
    private Long idsales;
    private String nik;
    private String nama;
    private int tahun;
    private int targettotal;
    private int tercapaitotal;
    private double tercapaipersentotal;
}
