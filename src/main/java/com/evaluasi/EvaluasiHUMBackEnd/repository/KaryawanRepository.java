package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KaryawanRepository extends JpaRepository<Karyawan,Long> {

    Karyawan findByNik(String nik);
    Karyawan findByIdkar(Long idkar);
    Karyawan findByJabatan(String jabatan);

    @Query("SELECT p FROM Karyawan p WHERE p.jabatan = :jabatan")
    Page<Karyawan> findByJabatan(String jabatan, Pageable pageable);
    @Query("SELECT p FROM Karyawan p WHERE p.nama = :nama")
    Page<Karyawan> findByNamak(String nama, Pageable pageable);

    Page<Karyawan> findByNamaAndJabatan(String nama, String jabatan, Pageable pageable);

    Optional<Karyawan> findByNama(String nama);
    Optional<Karyawan>findByEmail(String email);

    @Query("SELECT k FROM Karyawan k WHERE LOWER(k.nama) LIKE LOWER(CONCAT('%', :nama, '%'))")
    Page<Karyawan> findByNamaContainingIgnoreCase(String nama, Pageable pageable);

    @Query("SELECT k FROM Karyawan k WHERE LOWER(k.jabatan) LIKE LOWER(CONCAT('%', :jabatan, '%'))")
    Page<Karyawan> findByJabatanContainingIgnoreCase(String jabatan, Pageable pageable);

    @Query("SELECT k FROM Karyawan k WHERE LOWER(k.nama) = LOWER(:nama) AND LOWER(k.jabatan) LIKE LOWER(CONCAT('%', :jabatan, '%'))")
    Page<Karyawan> findByNamaAndJabatanContainingIgnoreCase(String nama, String jabatan, Pageable pageable);

}
