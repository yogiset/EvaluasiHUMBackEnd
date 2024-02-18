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
public class Jawaban {
    @Id
    @SequenceGenerator(name = "jawaban_sequence",sequenceName = "jawaban_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "jawaban_sequence")
    private Long idja;
    @Column(columnDefinition="text")
    private String jawaban;
    private Integer bobot;

//    @OneToOne(
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
//    )
//    @JoinColumn(
//            name = "kodepertanyaan",
//            referencedColumnName = "kodepertanyaan"
//    )
//    private Pertanyaan pertanyaan;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idper", nullable = false)
    private Pertanyaan pertanyaan;
}
