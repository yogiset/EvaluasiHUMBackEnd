package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Evaluasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluasiRepository extends JpaRepository<Evaluasi,Long> {

    Evaluasi findByKodeevaluasi(String kodeevaluasi);
}
