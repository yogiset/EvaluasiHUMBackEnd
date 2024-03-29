package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rule {
    @Id
    @SequenceGenerator(name = "rule_sequence",sequenceName = "rule_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "rule_sequence")
    private Long idrule;
    @Column(unique = true,nullable = false)
    private String koderule;
    private String rule;
    private String jabatan;

    @OneToOne(mappedBy = "rule")
    private Pertanyaan pertanyaan;


}
