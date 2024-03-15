package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Sales;
import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales,Long> {
    @Query("SELECT p FROM Sales p WHERE p.target = :target")
    Page<Sales> findByTarget(Integer target, Pageable pageable);
    @Query("SELECT p FROM Sales p WHERE p.tahun = :tahun")
    Page<Sales> findByTahun(Integer tahun, Pageable pageable);
    Page<Sales> findByTahunAndTarget(Integer tahun,Integer target, Pageable pageable);
}
