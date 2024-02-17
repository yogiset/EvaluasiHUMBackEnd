package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KaryawanRepository extends JpaRepository<Karyawan,Long> {

    Karyawan findByNik(String nik);
    Karyawan findByIdkar(Long idkar);
    Karyawan findByJabatan(String jabatan);
}
