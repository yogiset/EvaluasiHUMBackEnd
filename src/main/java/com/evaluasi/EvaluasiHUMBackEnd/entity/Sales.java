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
public class Sales {
    @Id
    @SequenceGenerator(name = "sales_sequence",sequenceName = "sales_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sales_sequence")
    private Long idsales;
    private Integer target;
    private Integer tahun;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "nik", nullable = false)
    private Karyawan karyawan;
}
