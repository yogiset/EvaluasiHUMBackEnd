package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userr")
public class User {
    @Id
    @SequenceGenerator(name = "userr_sequence",sequenceName = "userr_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userr_sequence")
    private Long iduser;
    private String kodeuser;
    private String username;
    private String password;
    private String role;
    private String status;
    private Instant created;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idkar", nullable = false)
    private Karyawan karyawan;
}