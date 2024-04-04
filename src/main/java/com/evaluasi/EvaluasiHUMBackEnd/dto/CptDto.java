package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CptDto {
    private Long idcpt;
    private String nik;
    private String nama;
    private int tahun;
    private int panolcustomer;
    private int coverage;
    private double coveragepersen;
    private double penetration;
    private double throughput;
    private double hitrate;
}
