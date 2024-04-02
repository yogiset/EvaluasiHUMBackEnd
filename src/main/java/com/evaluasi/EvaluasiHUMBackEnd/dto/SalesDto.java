package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDto {
    private Long idsales;
    private String nik;
    private String nama;
    private int target;
    private int tahun;
    private int tercapai;
    private double tercapaipersen;
    private String keterangan;
    private List<SalesDetailDto> salesDetailDtoList;
}
