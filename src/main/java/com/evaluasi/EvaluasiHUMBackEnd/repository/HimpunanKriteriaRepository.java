package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.HimpunanKriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HimpunanKriteriaRepository extends JpaRepository<HimpunanKriteria,Long> {

    @Query("SELECT p FROM HimpunanKriteria p WHERE p.nmkriteria = :nmkriteria")
    Page<HimpunanKriteria> findByNamaKriteria(String nmkriteria, Pageable pageable);

    @Query("SELECT p FROM HimpunanKriteria p WHERE LOWER(p.keterangan) LIKE LOWER(CONCAT('%', :keterangan, '%'))")
    Page<HimpunanKriteria> findByKeteranganContainingIgnoreCase(String keterangan, Pageable pageable);

    Page<HimpunanKriteria> findByNmkriteriaAndKeterangan(String nmkriteria,String keterangan, Pageable pageable);

    List<HimpunanKriteria> findByNmkriteria(String nmkriteria);

    @Query("SELECT k FROM HimpunanKriteria k WHERE LOWER(k.nmkriteria) LIKE LOWER(CONCAT('%', :nmkriteria, '%'))")
    Page<HimpunanKriteria> findByNmkriteriaContainingIgnoreCase(String nmkriteria, Pageable pageable);

    @Query("SELECT k FROM HimpunanKriteria k WHERE LOWER(k.keterangan) = LOWER(:keterangan) AND LOWER(k.nmkriteria) LIKE LOWER(CONCAT('%', :nmkriteria, '%'))")
    Page<HimpunanKriteria> findByKeteranganAndNmkriteriaContainingIgnoreCase(String keterangan, String nmkriteria, Pageable pageable);
}
