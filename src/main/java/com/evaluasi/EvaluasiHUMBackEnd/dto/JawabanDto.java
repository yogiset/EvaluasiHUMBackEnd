package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JawabanDto {
    private Long idja;
    private String jawaban;
    private Integer bobot;
    private Long idper;
}
