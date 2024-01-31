package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PertanyaanDto {
    private Long idper;
    private String kodepertanyaan;
    private String pertanyaan;
    private String jawaban;
    private Integer bobot;
}
