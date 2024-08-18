package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Sales;
import com.evaluasi.EvaluasiHUMBackEnd.entity.SalesDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesDetailRepository extends JpaRepository<SalesDetail,Long> {
    boolean existsByBulanAndSales_TahunAndSales_Karyawan_Nik(String bulan, int tahun, String nik);

    // Find SalesDetail by Tahun, Nama, and Bulan (ignoring case)
    @Query("SELECT sd FROM SalesDetail sd JOIN sd.sales s JOIN s.karyawan k " +
            "WHERE s.tahun = :tahun AND LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%')) AND LOWER(sd.bulan) LIKE LOWER(CONCAT('%', :bulan, '%'))")
    Page<SalesDetail> findByTahunAndNamaAndBulanContainingIgnoreCase(@Param("tahun") int tahun, @Param("nama") String nama, @Param("bulan") String bulan, Pageable pageable);

    // Find SalesDetail by Tahun and Nama (ignoring case)
    @Query("SELECT sd FROM SalesDetail sd JOIN sd.sales s JOIN s.karyawan k " +
            "WHERE s.tahun = :tahun AND LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%'))")
    Page<SalesDetail> findByTahunAndNamaContainingIgnoreCase(@Param("tahun") int tahun, @Param("nama") String nama, Pageable pageable);

    // Find SalesDetail by Tahun and Bulan
    @Query("SELECT sd FROM SalesDetail sd JOIN sd.sales s " +
            "WHERE s.tahun = :tahun AND sd.bulan = :bulan")
    Page<SalesDetail> findByTahunAndBulan(@Param("tahun") int tahun, @Param("bulan") String bulan, Pageable pageable);

    // Find SalesDetail by Nama and Bulan (ignoring case)
    @Query("SELECT sd FROM SalesDetail sd JOIN sd.sales s JOIN s.karyawan k " +
            "WHERE LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%')) AND LOWER(sd.bulan) LIKE LOWER(CONCAT('%', :bulan, '%'))")
    Page<SalesDetail> findByNamaAndBulanContainingIgnoreCase(@Param("nama") String nama, @Param("bulan") String bulan, Pageable pageable);

    // Find SalesDetail by Tahun
    @Query("SELECT sd FROM SalesDetail sd JOIN sd.sales s WHERE s.tahun = :tahun")
    Page<SalesDetail> findByTahun(@Param("tahun") int tahun, Pageable pageable);

    // Find SalesDetail by Bulan
    @Query("SELECT sd FROM SalesDetail sd WHERE LOWER(sd.bulan) LIKE LOWER(CONCAT('%', :bulan, '%'))")
    Page<SalesDetail> findByBulan(@Param("bulan") String bulan, Pageable pageable);

    // Find SalesDetail by Nama (ignoring case)
    @Query("SELECT sd FROM SalesDetail sd JOIN sd.sales s JOIN s.karyawan k " +
            "WHERE LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%'))")
    Page<SalesDetail> findByNamaContainingIgnoreCase(@Param("nama") String nama, Pageable pageable);

}
