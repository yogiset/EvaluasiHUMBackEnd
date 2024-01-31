package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pertanyaan {
    @Id
    @SequenceGenerator(name = "pertanyaan_sequence",sequenceName = "pertanyaan_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pertanyaan_sequence")
    private Long idper;
    private String kodepertanyaan;
    @Column(columnDefinition="text")
    private String pertanyaan;
    @Column(columnDefinition="text")
    private String jawaban;
    private Integer bobot;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idkar", nullable = false)
    private Karyawan karyawan;
}
