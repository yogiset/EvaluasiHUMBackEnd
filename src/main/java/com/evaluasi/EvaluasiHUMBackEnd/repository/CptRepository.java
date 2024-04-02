package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Cpt;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CptRepository extends JpaRepository<Cpt, Long> {
    @Query("SELECT p FROM Cpt p WHERE p.tahun = :tahun")
    Page<Cpt> findByTahun(Integer tahun, Pageable pageable);

    @Query("SELECT p FROM Cpt p WHERE p.coveragepersen = :coveragepersen")
    Page<Cpt> findByCoveragePersen(Integer coveragepersen, Pageable pageable);

    Page<Cpt> findByTahunAndCoveragepersen(Integer tahun,Integer coveragepersen, Pageable pageable);
}
