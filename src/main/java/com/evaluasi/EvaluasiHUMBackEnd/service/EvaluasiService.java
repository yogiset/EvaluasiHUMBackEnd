package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.EvaluasiDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserEvaResultDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Evaluasi;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.EvaluasiRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EvaluasiService {
    private final EvaluasiRepository evaluasiRepository;
    private final KaryawanRepository karyawanRepository;

    public ResponseEntity<Object> addEvaluasi(EvaluasiDto evaluasiDto) {
        try{
        log.info("Inside Add Evaluasi");
        Evaluasi evaluasi = new Evaluasi();
        evaluasi.setKodeevaluasi(evaluasiDto.getKodeevaluasi());
        evaluasi.setTanggalevaluasi(evaluasiDto.getTanggalevaluasi());
        evaluasi.setHasilevaluasi(evaluasiDto.getHasilevaluasi());
        evaluasi.setPerluditingkatkan(evaluasiDto.getPerluditingkatkan());

            Karyawan karyawan = karyawanRepository.findByNik(evaluasiDto.getNik());
            if (karyawan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Karyawan not found for NIK: " + evaluasiDto.getNik());
            }

            evaluasi.setKaryawan(karyawan);
            evaluasiRepository.save(evaluasi);

            return ResponseEntity.ok("Evaluasi created successfully");
        } catch (Exception e) {
            log.error("Error creating evaluasi", e);
            return ResponseEntity.status(500).body("Error creating evaluasi");
        }

    }

    public List<EvaluasiDto> showAll() {
        log.info("Inside Show All");
        List<Evaluasi> evaluasiList = evaluasiRepository.findAll();

        return evaluasiList.stream()
                .map(evaluasi -> {
                    EvaluasiDto evaluasiDto = new EvaluasiDto();
                    evaluasiDto.setIdeva(evaluasi.getIdeva());
                    evaluasiDto.setKodeevaluasi(evaluasi.getKodeevaluasi());
                    evaluasiDto.setTanggalevaluasi(evaluasi.getTanggalevaluasi());
                    evaluasiDto.setHasilevaluasi(evaluasi.getHasilevaluasi());
                    evaluasiDto.setPerluditingkatkan(evaluasi.getPerluditingkatkan());
                    evaluasiDto.setNik(evaluasi.getKaryawan().getNik());
                    return evaluasiDto;
                })
                .collect(Collectors.toList());

    }



    public ResponseEntity<Object> editEvaluasi(Long id,EvaluasiDto evaluasiDto) {
        try {
            log.info("Inside editEvaluasi");
            Optional<Evaluasi>optionalEvaluasi =evaluasiRepository.findById(id);
            Evaluasi evaluasi = optionalEvaluasi.get();
            evaluasi.setKodeevaluasi(evaluasiDto.getKodeevaluasi());
            evaluasi.setTanggalevaluasi(evaluasiDto.getTanggalevaluasi());
            evaluasi.setHasilevaluasi(evaluasiDto.getHasilevaluasi());
            evaluasi.setPerluditingkatkan(evaluasiDto.getPerluditingkatkan());
            Karyawan karyawan = karyawanRepository.findByNik(evaluasiDto.getNik());
            if (karyawan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Karyawan not found for NIK: " + evaluasiDto.getNik());
            }

            evaluasi.setKaryawan(karyawan);

            evaluasiRepository.save(evaluasi);
            return ResponseEntity.ok("Evaluasi edited successfully");

        }catch (Exception e){
            log.error("Error edited evaluasi", e);
            return ResponseEntity.status(500).body("Error edited evaluasi");
        }
    }

    public ResponseEntity<Object> hapusEvaluasi(Long id) {
        try {
            log.info("Inside hapus evaluasi");
            Optional<Evaluasi> optionalEvaluasi = evaluasiRepository.findById(id);

            if (optionalEvaluasi.isPresent()) {
                evaluasiRepository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted evaluasi");
            } else {
                return ResponseEntity.status(404).body("Evaluasi not found");
            }
        } catch (Exception e) {
            log.error("Error delete evaluasi", e);
            return ResponseEntity.status(500).body("Error delete evaluasi");
        }
    }


    public Page<UserEvaResultDto> showAllEvaluationWithPagination(int offset, int pageSize) {
        log.info("Inside showAllEvaluationWithPagination");

        Page<Evaluasi> evaluasiPage = evaluasiRepository.findAll(PageRequest.of(offset, pageSize));

        List<UserEvaResultDto> resultList = evaluasiPage.getContent().stream()
                .map(evaluasi -> {
                    UserEvaResultDto resultDto = new UserEvaResultDto();
                    Karyawan karyawan = evaluasi.getKaryawan();
                    resultDto.setIdkar(karyawan.getIdkar());
                    resultDto.setNik(karyawan.getNik());
                    resultDto.setNama(karyawan.getNama());
                    resultDto.setDivisi(karyawan.getDivisi());
                    resultDto.setJabatan(karyawan.getJabatan());
                    resultDto.setTanggalmasuk(karyawan.getTanggalmasuk());
                    resultDto.setMasakerja(karyawan.getMasakerja());
                    resultDto.setTingkatan(karyawan.getTingkatan());
                    resultDto.setKodeevaluasi(evaluasi.getKodeevaluasi());
                    resultDto.setTanggalevaluasi(evaluasi.getTanggalevaluasi());
                    resultDto.setHasilevaluasi(evaluasi.getHasilevaluasi());
                    resultDto.setPerluditingkatkan(evaluasi.getPerluditingkatkan());
                    return resultDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, evaluasiPage.getPageable(), evaluasiPage.getTotalElements());
    }


    public Page<UserEvaResultDto> showAllEvaluationWithPaginationAscTanggal(int offset, int pageSize) {
        log.info("Inside showAllEvaluationWithPaginationAscTaggal");

        Page<Evaluasi> evaluasiPage = evaluasiRepository.findAll(PageRequest.of(offset, pageSize, Sort.by("tanggalevaluasi").ascending()));

        List<UserEvaResultDto> resultList = evaluasiPage.getContent().stream()
                .map(evaluasi -> {
                    UserEvaResultDto resultDto = new UserEvaResultDto();
                    Karyawan karyawan = evaluasi.getKaryawan();
                    resultDto.setIdkar(karyawan.getIdkar());
                    resultDto.setNik(karyawan.getNik());
                    resultDto.setNama(karyawan.getNama());
                    resultDto.setDivisi(karyawan.getDivisi());
                    resultDto.setJabatan(karyawan.getJabatan());
                    resultDto.setTanggalmasuk(karyawan.getTanggalmasuk());
                    resultDto.setMasakerja(karyawan.getMasakerja());
                    resultDto.setTingkatan(karyawan.getTingkatan());
                    resultDto.setKodeevaluasi(evaluasi.getKodeevaluasi());
                    resultDto.setTanggalevaluasi(evaluasi.getTanggalevaluasi());
                    resultDto.setHasilevaluasi(evaluasi.getHasilevaluasi());
                    resultDto.setPerluditingkatkan(evaluasi.getPerluditingkatkan());
                    return resultDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, evaluasiPage.getPageable(), evaluasiPage.getTotalElements());
    }

    public Page<UserEvaResultDto> showAllEvaluationWithPaginationDescTanggal(int offset, int pageSize) {
        log.info("Inside showAllEvaluationWithPaginationDescTanggal");

        Page<Evaluasi> evaluasiPage = evaluasiRepository.findAll(PageRequest.of(offset, pageSize,Sort.by("tanggalevaluasi").descending()));

        List<UserEvaResultDto> resultList = evaluasiPage.getContent().stream()
                .map(evaluasi -> {
                    UserEvaResultDto resultDto = new UserEvaResultDto();
                    Karyawan karyawan = evaluasi.getKaryawan();
                    resultDto.setIdkar(karyawan.getIdkar());
                    resultDto.setNik(karyawan.getNik());
                    resultDto.setNama(karyawan.getNama());
                    resultDto.setDivisi(karyawan.getDivisi());
                    resultDto.setJabatan(karyawan.getJabatan());
                    resultDto.setTanggalmasuk(karyawan.getTanggalmasuk());
                    resultDto.setMasakerja(karyawan.getMasakerja());
                    resultDto.setTingkatan(karyawan.getTingkatan());
                    resultDto.setKodeevaluasi(evaluasi.getKodeevaluasi());
                    resultDto.setTanggalevaluasi(evaluasi.getTanggalevaluasi());
                    resultDto.setHasilevaluasi(evaluasi.getHasilevaluasi());
                    resultDto.setPerluditingkatkan(evaluasi.getPerluditingkatkan());
                    return resultDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, evaluasiPage.getPageable(), evaluasiPage.getTotalElements());
    }

    public EvaluasiDto findByIdEva(Long id) throws AllException {
        log.info("inside findbyideva");
        Evaluasi evaluasi = evaluasiRepository.findById(id).orElseThrow(() -> new AllException("Evaluasi with id " + id + " not found"));

        return mapEvaluasiToEvaluasiDto(evaluasi);
    }

    public EvaluasiDto mapEvaluasiToEvaluasiDto(Evaluasi evaluasi){
        EvaluasiDto evaluasiDto = new EvaluasiDto();
        evaluasiDto.setIdeva(evaluasi.getIdeva());
        evaluasiDto.setKodeevaluasi(evaluasi.getKodeevaluasi());
        evaluasiDto.setTanggalevaluasi(evaluasi.getTanggalevaluasi());
        evaluasiDto.setHasilevaluasi(evaluasi.getHasilevaluasi());
        evaluasiDto.setPerluditingkatkan(evaluasi.getPerluditingkatkan());

        if(evaluasi.getKaryawan() != null){
            evaluasiDto.setNik(evaluasi.getKaryawan().getNik());
        }

        return  evaluasiDto;
    }
}
