package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Evaluasi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EvaluasiRepository extends JpaRepository<Evaluasi,Long> {

    Evaluasi findByKodeevaluasi(String kodeevaluasi);
    Page<Evaluasi> findByKaryawanNamaContainingIgnoreCaseAndHasilevaluasi(String nama, String hasilevaluasi, Pageable pageable);
    Page<Evaluasi> findByKaryawanNamaContainingIgnoreCase(String nama, Pageable pageable);
    @Query("SELECT p FROM Evaluasi p WHERE p.hasilevaluasi = :hasilevaluasi")
    Page<Evaluasi> findByHasilEvaluasi(String hasilevaluasi, Pageable pageable);

    @Query("SELECT i FROM Evaluasi i WHERE i.tanggalevaluasi = :tanggalevaluasi")
    Page<Evaluasi> findByTanggalEvaluasi(LocalDate tanggalevaluasi, Pageable pageable);

    @Query("SELECT u FROM Evaluasi u WHERE u.karyawan.idkar = :idkar")
    Optional<Evaluasi> findByIdkar(@Param("idkar") Long idkar);
}
