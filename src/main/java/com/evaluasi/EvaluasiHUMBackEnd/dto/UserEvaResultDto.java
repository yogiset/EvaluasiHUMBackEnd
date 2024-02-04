package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEvaResultDto {
    private Long idkar;
    private String nik;
    private String nama;
    private String divisi;
    private String jabatan;
    private String cadangan1;
    private Integer cadangan2;
    private String kodeevaluasi;
    private LocalDate tanggalevaluasi;
    private String hasilevaluasi;
    private String perluditingkatkan;
}
