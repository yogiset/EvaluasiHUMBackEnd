package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Picos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PicosRepository extends JpaRepository<Picos, Long> {

}
