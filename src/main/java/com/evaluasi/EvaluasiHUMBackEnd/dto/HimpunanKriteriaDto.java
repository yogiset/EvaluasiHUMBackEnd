package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HimpunanKriteriaDto {
    private Long idhim;
    private String nmkriteria;
    private String nmhimpunan;
    private Integer nilai;
    private String keterangan;
}
