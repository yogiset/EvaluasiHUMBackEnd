package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class HasilEvaluasi {
    @Id
    @SequenceGenerator(name = "evaluasi_sequence",sequenceName = "evaluasi_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "evaluasi_sequence")
    private Long ideva;
    private String kodeevaluasi;
    private LocalDate tanggalevaluasi;
    private String hasilevaluasi;
    @Column(columnDefinition="text")
    private String perluditingkatkan;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idkar", nullable = false)
    private Karyawan karyawan;
}

