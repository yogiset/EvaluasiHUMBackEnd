package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Evaluasi;
import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluasiRepository extends JpaRepository<Evaluasi,Long> {

    Evaluasi findByKodeevaluasi(String kodeevaluasi);

    @Query("SELECT u FROM Evaluasi u WHERE u.karyawan.idkar = :idkar")
    Optional<Evaluasi> findByIdkar(@Param("idkar") Long idkar);
}
