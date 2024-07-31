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
public class HimpunanKriteria {
    @Id
    @SequenceGenerator(name = "himkriteria_sequence",sequenceName = "himkriteria_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "himkriteria_sequence")
    private Long idhim;
    private String nmkriteria;
    private String nmhimpunan;
    private Integer nilai;
    private String keterangan;
}
