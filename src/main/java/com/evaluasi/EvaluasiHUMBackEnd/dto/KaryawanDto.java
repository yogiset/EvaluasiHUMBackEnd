package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KaryawanDto {
    private Long idkar;
    private String nik;
    private String nama;
    private String divisi;
    private String jabatan;
    private LocalDate tanggalmasuk;
    private String masakerja;
    private String tingkatan;
}
