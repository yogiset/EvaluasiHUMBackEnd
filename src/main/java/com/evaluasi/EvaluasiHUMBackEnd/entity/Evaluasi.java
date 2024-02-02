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
public class Evaluasi {
    @Id
    @SequenceGenerator(name = "evaluasi_sequence",sequenceName = "evaluasi_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "evaluasi_sequence")
    private Long ideva;
    @Column(unique = true,nullable = false)
    private String kodeevaluasi;
    private LocalDate tanggalevaluasi;
    private String hasilevaluasi;
    @Column(columnDefinition="text")
    private String perluditingkatkan;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nik", nullable = false)
    private Karyawan karyawan;

    @OneToOne(mappedBy = "evaluasi")
    private Rule rule;
}

