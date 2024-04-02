package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PicosDto {
    private Long idpicos;
    private String nik;
    private String nama;
    private int tahun;
    private String bulan;
    private double pipelinestrength;
    private double lowtouchratio;
    private double crosssellratio;
    private double premiumcontribution;
}
