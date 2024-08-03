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
    private String jabatan;
    private int tahun;
    private int targettotal;
    private int tercapaitotal;
    private double tercapaipersentotal;
    private int targetgadus;
    private int tercapaigadus;
    private double tercapaipersengadus;
    private int targetpremium;
    private int tercapaipremium;
    private double tercapaipersenpremium;
    private int jumlahcustomer;
    private double jumlahvisit;
    private List<SalesDetailDto> salesDetailDtoList;
}
