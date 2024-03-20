package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.ChangeEmail;
import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.RuleDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Rule;
import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class KaryawanService {

    private final KaryawanRepository karyawanRepository;

    public Long getTotalKaryawan(){
        log.info("Inside getTotalKaryawan");
        return karyawanRepository.count();
    }
    public ResponseEntity<Object> addkaryawan(KaryawanDto karyawanDto,String nik) {
        log.info("inside add",karyawanDto);
        try{
            Karyawan opKaryawan = karyawanRepository.findByNik(nik);

            if (opKaryawan == null) {
                Karyawan karyawan = new Karyawan();
                karyawan.setNik(karyawanDto.getNik());
                karyawan.setNama(karyawanDto.getNama());
                karyawan.setDivisi(karyawanDto.getDivisi());
                karyawan.setJabatan(karyawanDto.getJabatan());
                karyawan.setEmail(karyawanDto.getEmail());
                karyawan.setTanggalmasuk(karyawanDto.getTanggalmasuk());

                String masakerjaString = karyawan.getMasakerja();
                String[] masakerjaParts = masakerjaString.split(" ");
                int years = Integer.parseInt(masakerjaParts[0]);

                String tingkat;
                if(years == 0){
                    tingkat = "Trial/Kontrak";
                }else if (years < 5) {
                    tingkat = "Junior";
                } else {
                    tingkat = "Senior";
                }
                karyawan.setTingkatan(tingkat);

                karyawanRepository.save(karyawan);

                return ResponseEntity.ok("New karyawan added successfully");
            } else {
                return ResponseEntity.status(404).body("Karyawan with "+nik+" is exist");
            }

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
            karyawan.setEmail(karyawanDto.getEmail());
            karyawan.setTanggalmasuk(karyawanDto.getTanggalmasuk());

            String masakerjaString = karyawan.getMasakerja();
            String[] masakerjaParts = masakerjaString.split(" ");
            int years = Integer.parseInt(masakerjaParts[0]);

            String tingkat;
            if(years == 0){
                tingkat = "Trial/Kontrak";
            }else if (years < 5) {
                tingkat = "Junior";
            } else {
                tingkat = "Senior";
            }
            karyawan.setTingkatan(tingkat);

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
    public KaryawanDto findByIdNama(String nama) throws AllException {
        log.info("Inside findbynama");
        Karyawan karyawan = karyawanRepository.findByNama(nama).orElseThrow(() -> new AllException("karyawan with nama " + nama + " not found"));
        return mapKaryawanToKaryawanDto(karyawan);
    }
    
    public KaryawanDto mapKaryawanToKaryawanDto(Karyawan karyawan) {
        KaryawanDto karyawanDto = new KaryawanDto();
        karyawanDto.setIdkar(karyawan.getIdkar());
        karyawanDto.setNik(karyawan.getNik());
        karyawanDto.setNama(karyawan.getNama());
        karyawanDto.setDivisi(karyawan.getDivisi());
        karyawanDto.setJabatan(karyawan.getJabatan());
        karyawanDto.setEmail(karyawan.getEmail());
        karyawanDto.setTanggalmasuk(karyawan.getTanggalmasuk());
        karyawanDto.setMasakerja(karyawan.getMasakerja());
        karyawanDto.setTingkatan(karyawan.getTingkatan());

        return karyawanDto;
    }

    public Page<KaryawanDto> showAllAndPaginationKaryawan(String jabatan, String order, int offset, int pageSize) {
        log.info("Inside showAllAndPaginationKaryawan");
        Page<Karyawan> karyawanPage;
        if (jabatan == null) {
            karyawanPage = karyawanRepository.findAll(PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idkar").descending() : Sort.by("idkar").ascending()));
        } else {
            karyawanPage = karyawanRepository.findByJabatan(jabatan,PageRequest.of(offset - 1,pageSize, "desc".equals(order) ? Sort.by("idkar").descending() : Sort.by("idkar").ascending()));
        }
                List<KaryawanDto> resultList = karyawanPage.getContent().stream()
                .map(karyawan -> {
                    KaryawanDto karyawanDto = new KaryawanDto();
                    karyawanDto.setIdkar(karyawan.getIdkar());
                    karyawanDto.setNik(karyawan.getNik());
                    karyawanDto.setNama(karyawan.getNama());
                    karyawanDto.setDivisi(karyawan.getDivisi());
                    karyawanDto.setJabatan(karyawan.getJabatan());
                    karyawanDto.setEmail(karyawan.getEmail());
                    karyawanDto.setTanggalmasuk(karyawan.getTanggalmasuk());
                    karyawanDto.setMasakerja(karyawan.getMasakerja());
                    karyawanDto.setTingkatan(karyawan.getTingkatan());
                    return karyawanDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, karyawanPage.getPageable(), karyawanPage.getTotalElements());

    }


    public ResponseEntity<Object> changeEmails(ChangeEmail changeEmail) {
        try {
            log.info("Change Emails");
            log.info("Received request with payload: {}", changeEmail);

            Long idkar = changeEmail.getIdkar();
            if (idkar != null) {
                Karyawan karyawan = karyawanRepository.findByIdkar(idkar);
                if (karyawan != null) {
                    String oldEmail = changeEmail.getOldemail();
                    String newEmail = changeEmail.getNewemail();
                    if (oldEmail.equals(karyawan.getEmail())) {
                        karyawan.setEmail(newEmail);
                        karyawanRepository.save(karyawan);
                        return ResponseEntity.ok(Collections.singletonMap("message", "Email updated successfully"));
                    } else {
                        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Incorrect old email"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Email not found"));
                }
            } else {
                log.error("Token is null or empty");
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Token is null or empty"));
            }
        } catch (Exception ex) {
            log.error("An error occurred while changing the email", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "An error occurred while changing the email"));
        }
    }
}

