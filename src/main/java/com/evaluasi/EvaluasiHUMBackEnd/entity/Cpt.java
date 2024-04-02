package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cpt {
    @Id
    @SequenceGenerator(name = "cpt_sequence",sequenceName = "cpt_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cpt_sequence")
    private Long idcpt;
    private int tahun;
    private int panolcustomer;
    private int coverage;
    private double coveragepersen;
    private int penetration;
    private String throughput;
    private double hitrate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nik", nullable = false)
    private Karyawan karyawan;
}
