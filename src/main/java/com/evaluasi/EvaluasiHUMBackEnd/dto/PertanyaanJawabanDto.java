package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PertanyaanJawabanDto {
    private Long idper;
    private String kodepertanyaan;
    private String koderule;
    private String rule;
    private String pertanyaan;
    private String jabatan;
    private List<JawabanDto> jawabanList;
}
