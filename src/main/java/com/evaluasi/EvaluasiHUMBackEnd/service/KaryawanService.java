package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                .map(item -> new KaryawanDto(item.getIdkar(),item.getNik(),item.getNama(),item.getDivisi(),item.getJabatan(),item.getCadangan1(),item.getCadangan2()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> addkaryawan(KaryawanDto karyawanDto) {
        log.info("inside add",karyawanDto);
        try{
            Karyawan karyawan = new Karyawan();
            karyawan.setNik(karyawanDto.getNik());
            karyawan.setNama(karyawanDto.getNama());
            karyawan.setDivisi(karyawanDto.getDivisi());
            karyawan.setJabatan(karyawanDto.getJabatan());
            karyawan.setCadangan1(karyawanDto.getCadangan1());
            karyawan.setCadangan2(karyawanDto.getCadangan2());

            karyawanRepository.save(karyawan);

            return ResponseEntity.ok("New karyawan added successfully");
        } catch (Exception e) {
            log.error("Error creating new karyawan", e);
            return ResponseEntity.status(500).body("Error creating new karyawan");
        }
    }

    public ResponseEntity<Object> editKaryawan(Long id, KaryawanDto karyawanDto) {
        try {
            log.info("Inside edit karyawan");
            Optional<Karyawan> optionalKaryawan = karyawanRepository.findById(id);
            Karyawan karyawan = optionalKaryawan.get();

            karyawan.setNik(karyawanDto.getNik());
            karyawan.setNama(karyawanDto.getNama());
            karyawan.setDivisi(karyawanDto.getDivisi());
            karyawan.setJabatan(karyawanDto.getJabatan());
            karyawan.setJabatan(karyawanDto.getJabatan());
            karyawan.setCadangan1(karyawanDto.getCadangan1());
            karyawan.setCadangan2(karyawanDto.getCadangan2());

            karyawanRepository.save(karyawan);
            return ResponseEntity.ok("Evaluasi edited successfully");

        }catch (Exception e){
            log.error("Error edited evaluasi", e);
            return ResponseEntity.status(500).body("Error edited evaluasi");
        }
    }

    public ResponseEntity<Object> hapusKaryawan(Long id) {
        try {
            log.info("Inside hapus karyawan");
            Optional<Karyawan> optionalKaryawan = karyawanRepository.findById(id);

            if (optionalKaryawan.isPresent()) {
                karyawanRepository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted karyawan");
            } else {
                return ResponseEntity.status(404).body("Karyawan not found");
            }
        } catch (Exception e) {
            log.error("Error delete karyawan ", e);
            return ResponseEntity.status(500).body("Error delete karyawan ");
        }
    }

    public KaryawanDto findByIdKar(Long id) throws AllException {
        log.info("Inside findbyidkar");
        Karyawan karyawan = karyawanRepository.findById(id).orElseThrow(() -> new AllException("karyawan with idkar " + id + " not found"));
         return mapKaryawanToKaryawanDto(karyawan);
    }
    
    public KaryawanDto mapKaryawanToKaryawanDto(Karyawan karyawan) {
        KaryawanDto karyawanDto = new KaryawanDto();
        karyawanDto.setIdkar(karyawan.getIdkar());
        karyawanDto.setNik(karyawan.getNik());
        karyawanDto.setNama(karyawan.getNama());
        karyawanDto.setDivisi(karyawan.getDivisi());
        karyawanDto.setJabatan(karyawan.getJabatan());
        karyawanDto.setCadangan1(karyawan.getCadangan1());
        karyawanDto.setCadangan2(karyawan.getCadangan2());

        return karyawanDto;
    }
}

