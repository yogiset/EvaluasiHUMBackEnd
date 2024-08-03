package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Cpt;
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

    @Query("SELECT s FROM Cpt s JOIN s.karyawan k WHERE LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%'))")
    Page<Cpt> findByNamaContainingIgnoreCase(String nama, Pageable pageable);

    @Query("SELECT s FROM Cpt s JOIN s.karyawan k WHERE s.tahun = :tahun AND LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%'))")
    Page<Cpt> findByTahunAndNamaContainingIgnoreCase(Integer tahun, String nama, Pageable pageable);
}
