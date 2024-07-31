package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.BobotKriteriaDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.HimpunanKriteriaDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.BobotKriteria;
import com.evaluasi.EvaluasiHUMBackEnd.entity.HimpunanKriteria;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.BobotKriteriaRepository;
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
public class BobotKriteriaService {
    private final BobotKriteriaRepository bobotKriteriaRepository;

        public ResponseEntity<Object> createBobot(BobotKriteriaDto bobotKriteriaDto) {
            log.info("Inside createBobot");
            try {

                if (bobotKriteriaRepository.existsByNmkriteria(bobotKriteriaDto.getNmkriteria())) {
                    return ResponseEntity.badRequest().body("Bobot Kriteria with name '" + bobotKriteriaDto.getNmkriteria() + "' already exists");
                }

                BobotKriteria bobotKriteria = new BobotKriteria();
                bobotKriteria.setNmkriteria(bobotKriteriaDto.getNmkriteria());
                bobotKriteria.setBobot(bobotKriteriaDto.getBobot());
                bobotKriteriaRepository.save(bobotKriteria);


                return ResponseEntity.ok("Bobot Kriteria created successfully");
            } catch (Exception e) {
                log.error("Error creating Bobot Kriteria", e);
                return ResponseEntity.status(500).body("Error creating Bobot Kriteria");
            }
        }

    public ResponseEntity<Object> editBobot(Long id, BobotKriteriaDto bobotKriteriaDto) {
        log.info("Inside editBobot");
        try {
            Optional<BobotKriteria> optionalBobotKriteria = bobotKriteriaRepository.findById(id);
            if (!optionalBobotKriteria.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bobot kriteria not found with id " + id);
            }
            BobotKriteria bobotKriteria = optionalBobotKriteria.get();
            bobotKriteria.setNmkriteria(bobotKriteriaDto.getNmkriteria());
            bobotKriteria.setBobot(bobotKriteriaDto.getBobot());
            bobotKriteriaRepository.save(bobotKriteria);

            return ResponseEntity.ok("Bobot Kriteria edited successfully");
        } catch (Exception e) {
            log.error("Error editing Bobot Kriteria", e);
            return ResponseEntity.status(500).body("Error editing Bobot Kriteria");
        }
    }

    public ResponseEntity<Object> deleteBobot(Long id) {
        log.info("Inside deleteBobot");
        try {
            Optional<BobotKriteria>optionalBobotKriteria = bobotKriteriaRepository.findById(id);
            if(optionalBobotKriteria.isPresent()){
                bobotKriteriaRepository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted bobot kriteria");
            }else {
                return ResponseEntity.status(404).body("Data bobot kriteria not found");
            }

        } catch (Exception e) {
            log.error("Error delete data bobot kriteria", e);
            return ResponseEntity.status(500).body("Error delete data bobot kriteria");
        }
    }

    public Page<BobotKriteriaDto> showAllAndPaginationBobot(String nmkriteria, Integer bobot, String order, int offset, int pageSize) {
        log.info("Inside showAllAndPaginationBobot");
        Page<BobotKriteria> bobotKriteriaPage;
        if (bobot != null && nmkriteria != null) {
            bobotKriteriaPage = bobotKriteriaRepository.findByBobotAndNmkriteria(bobot,nmkriteria, PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idbobot").descending() : Sort.by("idbobot").ascending()));
        } else if (bobot != null) {
            bobotKriteriaPage = bobotKriteriaRepository.findByBobot(bobot, PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idbobot").descending() : Sort.by("idbobot").ascending()));
        } else if (nmkriteria != null) {
            bobotKriteriaPage = bobotKriteriaRepository.findByNmkriteria(nmkriteria, PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idbobot").descending() : Sort.by("idbobot").ascending()));
        } else {
            bobotKriteriaPage = bobotKriteriaRepository.findAll(PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idbobot").descending() : Sort.by("idbobot").ascending()));
        }
        List<BobotKriteriaDto> resultList = bobotKriteriaPage.getContent().stream()
                .map(bobotKriteria -> {
                    BobotKriteriaDto bobotKriteriaDto = new BobotKriteriaDto();
                    bobotKriteriaDto.setIdbobot(bobotKriteria.getIdbobot());
                    bobotKriteriaDto.setNmkriteria(bobotKriteria.getNmkriteria());
                    bobotKriteriaDto.setBobot(bobotKriteria.getBobot());
                    return bobotKriteriaDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, bobotKriteriaPage.getPageable(), bobotKriteriaPage.getTotalElements());
    }

    public BobotKriteriaDto fetchBobotKriteriaDtoByIdbobot(Long id) throws AllException {
        log.info("fetchBobotKriteriaDtoByIdBobot");
        BobotKriteria bobotKriteria = bobotKriteriaRepository.findById(id).orElseThrow(() -> new AllException("Bobot Kriteria with idbobot " + id + " not found"));
        BobotKriteriaDto bobotKriteriaDto = new BobotKriteriaDto();
        bobotKriteriaDto.setIdbobot(bobotKriteria.getIdbobot());
        bobotKriteriaDto.setNmkriteria(bobotKriteria.getNmkriteria());
        bobotKriteriaDto.setBobot(bobotKriteria.getBobot());
        return bobotKriteriaDto;
    }
}
