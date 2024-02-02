package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Karyawan {
    @Id
    @SequenceGenerator(name = "karyawan_sequence",sequenceName = "karyawan_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "karyawan_sequence")
    private Long idkar;
    @Column(unique = true,nullable = false)
    private String nik;
    private String nama;
    private String divisi;
    private String jabatan;
    private String cadangan1;
    private Integer cadangan2;



    @OneToMany(mappedBy = "karyawan", cascade = CascadeType.ALL)
    private List<Pertanyaan> pertanyaanList;

    @OneToMany(mappedBy = "karyawan", cascade = CascadeType.ALL)
    private List<User> userList;

    @OneToMany(mappedBy = "karyawan", cascade = CascadeType.ALL)
    private List<Evaluasi> evaluasiList;

}
