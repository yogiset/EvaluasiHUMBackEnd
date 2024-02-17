package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Pertanyaan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PertanyaanRepository extends JpaRepository<Pertanyaan,Long> {
    Pertanyaan findByIdper(Long idper);
    Pertanyaan findByKodepertanyaan(String kodepertanyaan);


    @Query("SELECT p FROM Pertanyaan p WHERE p.jabatan = :jabatan")
    Page<Pertanyaan> findByJabatan(String jabatan, Pageable pageable);


//    @Query("SELECT u FROM Pertanyaan u WHERE u.karyawan.idkar = :idkar")
//    Optional<Pertanyaan> findByIdkar(@Param("idkar") Long idkar);
}
