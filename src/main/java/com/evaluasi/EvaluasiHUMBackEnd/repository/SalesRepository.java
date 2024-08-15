package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Sales;
import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales,Long> {

    @Query("SELECT p FROM Sales p WHERE p.tahun = :tahun")
    Page<Sales> findByTahun(Integer tahun, Pageable pageable);

    @Query("SELECT s FROM Sales s JOIN s.karyawan k WHERE LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%'))")
    Page<Sales> findByNamaContainingIgnoreCase(String nama, Pageable pageable);

    @Query("SELECT s FROM Sales s JOIN s.karyawan k WHERE s.tahun = :tahun AND LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%'))")
    Page<Sales> findByTahunAndNamaContainingIgnoreCase(Integer tahun, String nama, Pageable pageable);

    boolean existsByKaryawanAndTahun(Karyawan karyawan, int tahun);

    boolean existsBySalesDetails_BulanAndTahunAndKaryawan_Nik(String bulan, int tahun, String nik);

}
