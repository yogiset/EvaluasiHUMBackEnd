package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesDetail {
    @Id
    @SequenceGenerator(name = "salesdetail_sequence",sequenceName = "salesdetail_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "salesdetail_sequence")
    private Long id;
    private String bulan;
    private int targetblntotal;
    private int tercapaiitotal;
    private double tercapaipersenntotal;
    private int targetblngadus;
    private int tercapaiigadus;
    private double tercapaipersenngadus;
    private int targetblnpremium;
    private int tercapaiipremium;
    private double tercapaipersennpremium;
    private double jumlahvisit;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idsales", nullable = false)
    private Sales sales;
}
