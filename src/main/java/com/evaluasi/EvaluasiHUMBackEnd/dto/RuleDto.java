package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleDto {
    private Long idrule;
    private String koderule;
    private String rule;
    private String kodeevaluasi;
}
