package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDetailDto {
    private Long id;
    private String bulan;
    private int targetbln;
    private int tercapaii;
    private String tercapaipersenn;
    private Long idsales;
}
