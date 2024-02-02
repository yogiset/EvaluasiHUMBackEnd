package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KaryawanDto {
    private Long idkar;
    private String nik;
    private String nama;
    private String divisi;
    private String jabatan;
    private String cadangan1;
    private Integer cadangan2;
}
