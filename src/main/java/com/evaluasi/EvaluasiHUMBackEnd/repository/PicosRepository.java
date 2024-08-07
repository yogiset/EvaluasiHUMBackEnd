package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Picos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PicosRepository extends JpaRepository<Picos, Long> {
    @Query("SELECT p FROM Picos p WHERE p.tahun = :tahun")
    Page<Picos> findByTahun(Integer tahun, Pageable pageable);

    @Query("SELECT p FROM Picos p WHERE p.bulan = :bulan")
    Page<Picos> findByBulan(String bulan, Pageable pageable);

    Page<Picos> findByTahunAndBulan(Integer tahun,String bulan, Pageable pageable);

    @Query("SELECT s FROM Picos s JOIN s.karyawan k WHERE LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%'))")
    Page<Picos> findByNamaContainingIgnoreCase(String nama, Pageable pageable);

    @Query("SELECT s FROM Picos s JOIN s.karyawan k WHERE s.tahun = :tahun AND LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%'))")
    Page<Picos> findByTahunAndNamaContainingIgnoreCase(Integer tahun, String nama, Pageable pageable);
}
