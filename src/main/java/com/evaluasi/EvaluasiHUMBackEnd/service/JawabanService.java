package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.JawabanDto;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Jawaban;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Pertanyaan;

import com.evaluasi.EvaluasiHUMBackEnd.dto.PertanyaanDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Jawaban;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Pertanyaan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Rule;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.JawabanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.PertanyaanRepository;
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
public class JawabanService {
//    private final JawabanRepository jawabanRepository;
//    private final PertanyaanRepository pertanyaanRepository;
//
//    public ResponseEntity<Object> addJawaban(JawabanDto jawabanDto) {
//        try{
//            log.info("Inside Add Jawaban");
//            Jawaban jawaban = new Jawaban();
//            jawaban.setJawaban(jawabanDto.getJawaban());
//            jawaban.setBobot(jawabanDto.getBobot());
//            Pertanyaan pertanyaan = pertanyaanRepository.findByIdper(jawabanDto.getIdper());
//            if(pertanyaan == null){
//                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pertanyaan not found for kodepertanyaan" +jawabanDto.getIdper());
//            }
//           jawaban.setPertanyaan(pertanyaan);
//            jawabanRepository.save(jawaban);
//
//            return ResponseEntity.ok("Jawaban created successfully");
//        } catch (Exception e) {
//            log.error("Error creating jawaban", e);
//            return ResponseEntity.status(500).body("Error creating jawaban");
//        }
//    }
//
//    public List<JawabanDto> showAll() {
//        log.info("Inside Show All");
//        List<Jawaban> jawabanList = jawabanRepository.findAll();
//
//        return jawabanList.stream()
//                .map(jawaban -> {
//                    JawabanDto jawabanDto = new JawabanDto();
//                    jawabanDto.setIdja(jawaban.getIdja());
//                    jawabanDto.setJawaban(jawaban.getJawaban());
//                    jawabanDto.setBobot(jawaban.getBobot());
//                    jawabanDto.setIdper(jawaban.getPertanyaan().getIdper());
//                    return jawabanDto;
//                })
//                .collect(Collectors.toList());
//    }
//
//    public ResponseEntity<Object> editJawaban(Long id, JawabanDto jawabanDto) {
//        try {
//            log.info("Inside editJawaban");
//            Optional<Jawaban> optionalJawaban =jawabanRepository.findById(id);
//            Jawaban jawaban = optionalJawaban.get();
//            jawaban.setJawaban(jawabanDto.getJawaban());
//            jawaban.setBobot(jawabanDto.getBobot());
//            jawabanRepository.save(jawaban);
//            return ResponseEntity.ok("Jawaban edited successfully");
//
//        }catch (Exception e){
//            log.error("Error edited jawaban", e);
//            return ResponseEntity.status(500).body("Error edited jawaban");
//        }
//    }
//
//    public ResponseEntity<Object> hapusJawaban(Long id) {
//        try {
//            log.info("Inside hapus jawaban");
//            Optional<Jawaban> optionalJawaban = jawabanRepository.findById(id);
//
//            if (optionalJawaban.isPresent()) {
//                jawabanRepository.deleteById(id);
//                return ResponseEntity.ok("Successfully deleted jawaban");
//            } else {
//                return ResponseEntity.status(404).body("Jawaban not found");
//            }
//        } catch (Exception e) {
//            log.error("Error delete jawaban", e);
//            return ResponseEntity.status(500).body("Error delete jawaban");
//        }
//    }
//
//    public JawabanDto findByIdJawab(Long id) throws AllException {
//        log.info("inside findbyidjawab");
//        Jawaban jawaban = jawabanRepository.findById(id).orElseThrow(() -> new AllException("Jawaban with id " + id + " not found"));
//
//        return mapJawabanToJawabanDto(jawaban);
//    }
//
//    private JawabanDto mapJawabanToJawabanDto(Jawaban jawaban) {
//        JawabanDto jawabanDto = new JawabanDto();
//        jawabanDto.setIdja(jawaban.getIdja());
//        jawabanDto.setJawaban(jawaban.getJawaban());
//        jawabanDto.setBobot(jawaban.getBobot());
//        if(jawaban.getPertanyaan() != null){
//            jawabanDto.setIdper(jawaban.getPertanyaan().getIdper());
//        }
//
//        return jawabanDto;
//    }
//
//
//    public Page<JawabanDto> showAllJawabanPagination(int offset, int pageSize) {
//        log.info("Inside showAllJawabanPagination");
//
//        Page<Jawaban> jawabanPage = jawabanRepository.findAll(PageRequest.of(offset, pageSize));
//
//        List<JawabanDto> resultList = jawabanPage.getContent().stream()
//                .map(jawaban -> {
//                    JawabanDto jawabanDto = new JawabanDto();
//                    Pertanyaan pertanyaan = jawaban.getPertanyaan();
//
//                    jawabanDto.setIdja(jawaban.getIdja());
//                    jawabanDto.setJawaban(jawaban.getJawaban());
//                    jawabanDto.setBobot(jawaban.getBobot());
//                    jawabanDto.setIdper(pertanyaan.getIdper());
//                    return jawabanDto;
//                })
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(resultList, jawabanPage.getPageable(), jawabanPage.getTotalElements());
//    }
//
//    public Page<JawabanDto> showAllJawabanPaginationAscBobot(int offset, int pageSize) {
//        log.info("Inside showAllJawabanPaginationAscIdper");
//
//        Page<Jawaban> jawabanPage = jawabanRepository.findAll(PageRequest.of(offset, pageSize, Sort.by("bobot").ascending()));
//
//        List<JawabanDto> resultList = jawabanPage.getContent().stream()
//                .map(jawaban -> {
//                    JawabanDto jawabanDto = new JawabanDto();
//                    Pertanyaan pertanyaan = jawaban.getPertanyaan();
//
//                    jawabanDto.setIdja(jawaban.getIdja());
//                    jawabanDto.setJawaban(jawaban.getJawaban());
//                    jawabanDto.setBobot(jawaban.getBobot());
//                    jawabanDto.setIdper(pertanyaan.getIdper());
//                    return jawabanDto;
//                })
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(resultList, jawabanPage.getPageable(), jawabanPage.getTotalElements());
//    }
//
//    public Page<JawabanDto> showAllJawabanPaginationDescBobot(int offset, int pageSize) {
//        log.info("Inside showAllJawabanPaginationDescIdper");
//
//        Page<Jawaban> jawabanPage = jawabanRepository.findAll(PageRequest.of(offset, pageSize,Sort.by("bobot").descending()));
//
//        List<JawabanDto> resultList = jawabanPage.getContent().stream()
//                .map(jawaban -> {
//                    JawabanDto jawabanDto = new JawabanDto();
//                    Pertanyaan pertanyaan = jawaban.getPertanyaan();
//
//                    jawabanDto.setIdja(jawaban.getIdja());
//                    jawabanDto.setJawaban(jawaban.getJawaban());
//                    jawabanDto.setBobot(jawaban.getBobot());
//                    jawabanDto.setIdper(pertanyaan.getIdper());
//                    return jawabanDto;
//                })
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(resultList, jawabanPage.getPageable(), jawabanPage.getTotalElements());
//    }
}
