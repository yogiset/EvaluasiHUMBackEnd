package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Cpt;
import com.evaluasi.EvaluasiHUMBackEnd.entity.HimpunanKriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HimpunanKriteriaRepository extends JpaRepository<HimpunanKriteria,Long> {

    @Query("SELECT p FROM HimpunanKriteria p WHERE p.nilai = :nilai")
    Page<HimpunanKriteria> findByNilai(Integer nilai, Pageable pageable);

    @Query("SELECT p FROM HimpunanKriteria p WHERE p.keterangan = :keterangan")
    Page<HimpunanKriteria> findByKeterangan(String keterangan, Pageable pageable);

    Page<HimpunanKriteria> findByNilaiAndKeterangan(Integer nilai,String keterangan, Pageable pageable);
}
