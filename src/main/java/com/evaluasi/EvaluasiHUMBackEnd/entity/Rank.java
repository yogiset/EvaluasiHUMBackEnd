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
public class Rank {
    @Id
    @SequenceGenerator(name = "rank_sequence",sequenceName = "rank_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "rank_sequence")
    private Long idrank;
    private int nilai;
    private String predikat;
    private String keterangan;


    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "idsales",
            referencedColumnName = "idsales"
    )
    private Sales sales;
}
