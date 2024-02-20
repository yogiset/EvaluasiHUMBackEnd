package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String jabatan;

    @OneToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "koderule",
            referencedColumnName = "koderule"
    )
    private Rule rule;

    @OneToMany(mappedBy = "pertanyaan", cascade = CascadeType.ALL)
    private List<Jawaban> jawabanList;
}
