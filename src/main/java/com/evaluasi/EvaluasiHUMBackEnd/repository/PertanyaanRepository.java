package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Pertanyaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PertanyaanRepository extends JpaRepository<Pertanyaan,Long> {

    @Query("SELECT u FROM Pertanyaan u WHERE u.karyawan.idkar = :idkar")
    Optional<Pertanyaan> findByIdkar(@Param("idkar") Long idkar);
}
