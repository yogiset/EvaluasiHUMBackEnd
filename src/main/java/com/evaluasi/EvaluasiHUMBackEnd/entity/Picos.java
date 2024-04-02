package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Picos {
    @Id
    @SequenceGenerator(name = "picos_sequence",sequenceName = "picos_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "picos_sequence")
    private Long idpicos;
    private int tahun;
    private String bulan;
    private double pipelinestrength;
    private double lowtouchratio;
    private double crosssellratio;
    private double premiumcontribution;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nik", nullable = false)
    private Karyawan karyawan;
}
