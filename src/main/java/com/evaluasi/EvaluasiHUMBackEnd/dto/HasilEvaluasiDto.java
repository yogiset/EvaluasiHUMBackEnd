package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasilEvaluasiDto {
    private Long ideva;
    private String kodeevaluasi;
    private LocalDate tanggalevaluasi;
    private String hasilevaluasi;
    private String perluditingkatkan;
}
