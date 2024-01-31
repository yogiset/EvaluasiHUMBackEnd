package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class KaryawanService {

    private final KaryawanRepository karyawanRepository;


    public List<KaryawanDto> showall() {
        log.info("inside showall");
        List<Karyawan>karyawanList= karyawanRepository.findAll();

        return karyawanList.stream()
                .map(item -> new KaryawanDto(item.getIdkar(),item.getNik(),item.getNama(),item.getJabatan()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> addkaryawan(KaryawanDto karyawanDto) {
        log.info("inside add",karyawanDto);
        try{
            Karyawan karyawan = new Karyawan();
            karyawan.setNik(karyawanDto.getNik());
            karyawan.setNama(karyawanDto.getNama());
            karyawan.setJabatan(karyawanDto.getJabatan());
            karyawanRepository.save(karyawan);

            return ResponseEntity.ok("New karyawan added successfully");
        } catch (Exception e) {
            log.error("Error creating new karyawan", e);
            return ResponseEntity.status(500).body("Error creating new karyawan");
        }
    }
}

