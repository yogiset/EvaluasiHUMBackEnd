package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.PertanyaanDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Pertanyaan;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.PertanyaanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PertanyaanService {
    private final PertanyaanRepository pertanyaanRepository;
    private final KaryawanRepository karyawanRepository;

    public ResponseEntity<Object> addPertanyaan(PertanyaanDto pertanyaanDto) {
        try{
            log.info("Inside Add Pertanyaan");
            Pertanyaan pertanyaan = new Pertanyaan();
            pertanyaan.setKodepertanyaan(pertanyaanDto.getKodepertanyaan());
            pertanyaan.setPertanyaan(pertanyaanDto.getPertanyaan());
            pertanyaan.setJawaban(pertanyaanDto.getJawaban());
            pertanyaan.setBobot(pertanyaanDto.getBobot());


            Karyawan karyawan = karyawanRepository.findByNik(pertanyaanDto.getNik());
            if (karyawan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Karyawan not found for NIK: " + pertanyaanDto.getNik());
            }

            pertanyaan.setKaryawan(karyawan);
            pertanyaanRepository.save(pertanyaan);

            return ResponseEntity.ok("Pertanyaan created successfully");
        } catch (Exception e) {
            log.error("Error creating pertanyaan", e);
            return ResponseEntity.status(500).body("Error creating pertanyaan");
        }

    }

    public List<PertanyaanDto> showAll() {
        log.info("Inside showall");
        List<Pertanyaan>pertanyaanList = pertanyaanRepository.findAll();

        return pertanyaanList.stream()
                .map(tanya -> {
                    PertanyaanDto pertanyaanDto = new PertanyaanDto();
                    pertanyaanDto.setIdper(tanya.getIdper());
                    pertanyaanDto.setKodepertanyaan(tanya.getKodepertanyaan());
                    pertanyaanDto.setPertanyaan(tanya.getPertanyaan());
                    pertanyaanDto.setJawaban(tanya.getJawaban());
                    pertanyaanDto.setBobot(tanya.getBobot());
                    pertanyaanDto.setNik(tanya.getKaryawan().getNik());
                    return pertanyaanDto;
                })
                .collect(Collectors.toList());
    }
}
