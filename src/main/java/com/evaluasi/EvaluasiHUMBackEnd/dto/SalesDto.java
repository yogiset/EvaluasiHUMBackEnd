package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDto {
    private Long idsales;
    private String nik;
    private String nama;
    private Integer target;
    private Integer tahun;
}
