package com.evaluasi.EvaluasiHUMBackEnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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
    private LocalDate tanggalmasuk;
    private String masakerja;
    private String tingkatan;

    public String getMasakerja() {
        LocalDate today = LocalDate.now();
        long years = ChronoUnit.YEARS.between(this.tanggalmasuk, today);
        long months = ChronoUnit.MONTHS.between(this.tanggalmasuk.plusYears(years), today);
        long days = ChronoUnit.DAYS.between(this.tanggalmasuk.plusYears(years).plusMonths(months), today);

        return years + " Tahun, " + months + " Bulan, " + days + " Hari";
    }


    @OneToMany(mappedBy = "karyawan", cascade = CascadeType.ALL)
    private List<User> userList;

    @OneToMany(mappedBy = "karyawan", cascade = CascadeType.ALL)
    private List<Evaluasi> evaluasiList;


}
