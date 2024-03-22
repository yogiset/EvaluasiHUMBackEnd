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
    private Integer targetbln;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idsales", nullable = false)
    private Sales sales;
}
