package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.HimpunanKriteriaDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.HimpunanKriteria;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.HimpunanKriteriaRepository;
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
@Slf4j
@RequiredArgsConstructor
public class HimpunanKriteriaService {
    private final HimpunanKriteriaRepository himpunanKriteriaRepository;


    public ResponseEntity<Object> createHim(HimpunanKriteriaDto himpunanKriteriaDto) {
        log.info("Inside createHim");
        try {
            HimpunanKriteria himpunanKriteria = new HimpunanKriteria();
            himpunanKriteria.setNmkriteria(himpunanKriteriaDto.getNmkriteria());
            himpunanKriteria.setNmhimpunan(himpunanKriteriaDto.getNmhimpunan());
            himpunanKriteria.setNilai(himpunanKriteriaDto.getNilai());
            himpunanKriteria.setKeterangan(himpunanKriteriaDto.getKeterangan());
            himpunanKriteriaRepository.save(himpunanKriteria);


            return ResponseEntity.ok("Himpunan Kriteria created successfully");
        } catch (Exception e) {
            log.error("Error creating Himpunan Kriteria", e);
            return ResponseEntity.status(500).body("Error creating Himpunan Kriteria");
        }
    }

    public ResponseEntity<Object> editHim(Long id,HimpunanKriteriaDto himpunanKriteriaDto) {
        log.info("Inside editHim");
        try {
        Optional<HimpunanKriteria>optionalHimpunanKriteria = himpunanKriteriaRepository.findById(id);
        if (!optionalHimpunanKriteria.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Himpunan kriteria not found with id " + id);
        }
        HimpunanKriteria himpunanKriteria = optionalHimpunanKriteria.get();
            himpunanKriteria.setNmkriteria(himpunanKriteriaDto.getNmkriteria());
            himpunanKriteria.setNmhimpunan(himpunanKriteriaDto.getNmhimpunan());
            himpunanKriteria.setNilai(himpunanKriteriaDto.getNilai());
            himpunanKriteria.setKeterangan(himpunanKriteriaDto.getKeterangan());
            himpunanKriteriaRepository.save(himpunanKriteria);

            return ResponseEntity.ok("Himpunan Kriteria edited successfully");
        } catch (Exception e) {
            log.error("Error editing Himpunan Kriteria", e);
            return ResponseEntity.status(500).body("Error editing Himpunan Kriteria");
        }

    }


    public ResponseEntity<Object> deleteHim(Long id) {
        log.info("Inside deleteHim");
        try {
            Optional<HimpunanKriteria>optionalHimpunanKriteria = himpunanKriteriaRepository.findById(id);
            if(optionalHimpunanKriteria.isPresent()){
                himpunanKriteriaRepository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted data himpunan kriteria");
            }else {
                return ResponseEntity.status(404).body("Data himpunan kriteria not found");
            }

        } catch (Exception e) {
            log.error("Error delete data himpunan kriteria", e);
            return ResponseEntity.status(500).body("Error delete data himpunan kriteria");
        }
    }

    public Page<HimpunanKriteriaDto> showAllAndPaginationHim(String keterangan, String nmkriteria, String order, int offset, int pageSize) {
        log.info("Inside showAllAndPaginationHim");
        Page<HimpunanKriteria> himpunanKriteriaPage;
        if (keterangan != null && nmkriteria != null) {
            himpunanKriteriaPage = himpunanKriteriaRepository.findByKeteranganAndNmkriteriaContainingIgnoreCase(keterangan,nmkriteria, PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idhim").descending() : Sort.by("idhim").ascending()));
        } else if (nmkriteria != null) {
            himpunanKriteriaPage = himpunanKriteriaRepository.findByNmkriteriaContainingIgnoreCase(nmkriteria, PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idhim").descending() : Sort.by("idhim").ascending()));
        } else if (keterangan != null) {
            himpunanKriteriaPage = himpunanKriteriaRepository.findByKeteranganContainingIgnoreCase(keterangan, PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idhim").descending() : Sort.by("idhim").ascending()));
        } else {
            himpunanKriteriaPage = himpunanKriteriaRepository.findAll(PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idhim").descending() : Sort.by("idhim").ascending()));
        }
        List<HimpunanKriteriaDto> resultList = himpunanKriteriaPage.getContent().stream()
                .map(himpunanKriteria -> {
                    HimpunanKriteriaDto himpunanKriteriaDto = new HimpunanKriteriaDto();
                    himpunanKriteriaDto.setIdhim(himpunanKriteria.getIdhim());
                    himpunanKriteriaDto.setNmkriteria(himpunanKriteria.getNmkriteria());
                    himpunanKriteriaDto.setNmhimpunan(himpunanKriteria.getNmhimpunan());
                    himpunanKriteriaDto.setNilai(himpunanKriteria.getNilai());
                    himpunanKriteriaDto.setKeterangan(himpunanKriteria.getKeterangan());
                    return himpunanKriteriaDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, himpunanKriteriaPage.getPageable(), himpunanKriteriaPage.getTotalElements());

    }

    public HimpunanKriteriaDto fetchHimpunanKriteriaDtoByIdhim(Long id) throws AllException {
        log.info("fetchHimpunanKriteriaDtoByIdhim");
        HimpunanKriteria himpunanKriteria = himpunanKriteriaRepository.findById(id).orElseThrow(() -> new AllException("Himpunan Kriteria with idhim " + id + " not found"));
        HimpunanKriteriaDto himpunanKriteriaDto = new HimpunanKriteriaDto();
        himpunanKriteriaDto.setIdhim(himpunanKriteria.getIdhim());
        himpunanKriteriaDto.setNmkriteria(himpunanKriteria.getNmkriteria());
        himpunanKriteriaDto.setNmhimpunan(himpunanKriteria.getNmhimpunan());
        himpunanKriteriaDto.setNilai(himpunanKriteria.getNilai());
        himpunanKriteriaDto.setKeterangan(himpunanKriteria.getKeterangan());
        return himpunanKriteriaDto;

    }
}
