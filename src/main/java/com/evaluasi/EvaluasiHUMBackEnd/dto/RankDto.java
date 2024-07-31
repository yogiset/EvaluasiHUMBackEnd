package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankDto {
    private Long idrank;
    private Long idsales;
    private String nama;
    private int nilai;
    private String predikat;
    private String keterangan;
    private int tahun;
    
}
