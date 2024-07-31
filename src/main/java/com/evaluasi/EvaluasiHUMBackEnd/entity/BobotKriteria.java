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
public class BobotKriteria {
    @Id
    @SequenceGenerator(name = "bobotkriteria_sequence",sequenceName = "bobotkriteria_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "bobotkriteria_sequence")
    private Long idbobot;
    private String nmkriteria;
    private Integer bobot;
}
