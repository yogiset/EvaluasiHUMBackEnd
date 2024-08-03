package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.BobotKriteria;
import com.evaluasi.EvaluasiHUMBackEnd.entity.HimpunanKriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BobotKriteriaRepository  extends JpaRepository<BobotKriteria, Long> {

    boolean existsByNmkriteria(String nmkriteria);

    @Query("SELECT k FROM BobotKriteria k WHERE k.bobot = :bobot")
    Page<BobotKriteria> findByBobot(Integer bobot, Pageable pageable);

    @Query("SELECT p FROM BobotKriteria p WHERE p.nmkriteria = :nmkriteria")
    Page<BobotKriteria> findByNmkriteria(String nmkriteria, Pageable pageable);

    Page<BobotKriteria> findByBobotAndNmkriteria(Integer bobot,String nmkriteria, Pageable pageable);

    List<BobotKriteria> findByNmkriteria(String nmkriteria);

    @Query("SELECT k FROM BobotKriteria k WHERE LOWER(k.nmkriteria) LIKE LOWER(CONCAT('%', :nmkriteria, '%'))")
    Page<BobotKriteria> findByNmkriteriaContainingIgnoreCase(String nmkriteria, Pageable pageable);

    @Query("SELECT k FROM BobotKriteria k WHERE k.bobot = :bobot AND LOWER(k.nmkriteria) LIKE LOWER(CONCAT('%', :nmkriteria, '%'))")
    Page<BobotKriteria> findByBobotAndNmkriteriaContainingIgnoreCase(Integer bobot, String nmkriteria, Pageable pageable);

}
