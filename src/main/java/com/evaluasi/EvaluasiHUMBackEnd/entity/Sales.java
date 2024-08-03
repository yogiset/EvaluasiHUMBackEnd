package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sales {
    @Id
    @SequenceGenerator(name = "sales_sequence",sequenceName = "sales_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sales_sequence")
    private Long idsales;
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

    @OneToMany(mappedBy = "sales",cascade = CascadeType.ALL)
    private List<SalesDetail>salesDetails;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nik", nullable = false)
    private Karyawan karyawan;

}

