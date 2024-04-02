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
    private int target;
    private int tahun;
    private int tercapai;
    private double tercapaipersen;
    private String keterangan;

    @OneToMany(mappedBy = "sales",cascade = CascadeType.ALL)
    private List<SalesDetail>salesDetails;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nik", nullable = false)
    private Karyawan karyawan;
}

