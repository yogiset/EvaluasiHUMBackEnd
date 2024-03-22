package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.*;
import com.evaluasi.EvaluasiHUMBackEnd.entity.*;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.JawabanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.PertanyaanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.RemoteCIDRFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PertanyaanService {
    private final PertanyaanRepository pertanyaanRepository;
    private final JawabanRepository jawabanRepository;
    private final RuleRepository ruleRepository;

//    public ResponseEntity<Object> addPertanyaan(PertanyaanDto pertanyaanDto) {
//        try{
//            log.info("Inside Add Pertanyaan");
//            Pertanyaan pertanyaan = new Pertanyaan();
//            pertanyaan.setKodepertanyaan(pertanyaanDto.getKodepertanyaan());
//            pertanyaan.setPertanyaan(pertanyaanDto.getPertanyaan());
//            pertanyaan.setJabatan(pertanyaanDto.getJabatan());
//            Rule rule = ruleRepository.findByKoderule(pertanyaanDto.getKoderule());
//            if(rule == null){
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rule not found for koderule: " + pertanyaanDto.getKoderule());
//            }
//            pertanyaan.setRule(rule);
//            pertanyaanRepository.save(pertanyaan);
//
//            return ResponseEntity.ok("Pertanyaan created successfully");
//        } catch (Exception e) {
//            log.error("Error creating pertanyaan", e);
//            return ResponseEntity.status(500).body("Error creating pertanyaan");
//        }
//
//    }
//
//    public ResponseEntity<Object> editPertanyaan(Long id, PertanyaanDto pertanyaanDto) {
//        try {
//            log.info("Inside edit pertanyaan");
//            Optional<Pertanyaan> optionalPertanyaan = pertanyaanRepository.findById(id);
//            Pertanyaan pertanyaan = optionalPertanyaan.get();
//
//            pertanyaan.setKodepertanyaan(pertanyaanDto.getKodepertanyaan());
//            pertanyaan.setPertanyaan(pertanyaanDto.getPertanyaan());
//            pertanyaan.setJabatan(pertanyaanDto.getJabatan());
//            Rule rule = ruleRepository.findByKoderule(pertanyaanDto.getKoderule());
//            if(rule == null){
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rule not found for koderule: " + pertanyaanDto.getKoderule());
//            }
//            pertanyaan.setRule(rule);
//
//            pertanyaanRepository.save(pertanyaan);
//            return ResponseEntity.ok("Pertanyaan edited successfully");
//
//        }catch (Exception e){
//            log.error("Error edited pertanyaan", e);
//            return ResponseEntity.status(500).body("Error edited pertanyaan");
//        }
//    }
//
//    public ResponseEntity<Object> hapusPertanyaan(Long id) {
//        try {
//            log.info("Inside hapus pertanyaan");
//            Optional<Pertanyaan> optionalPertanyaan = pertanyaanRepository.findById(id);
//
//            if (optionalPertanyaan.isPresent()) {
//                pertanyaanRepository.deleteById(id);
//                return ResponseEntity.ok("Successfully deleted pertanyaan");
//            } else {
//                return ResponseEntity.status(404).body("Pertanyaan not found");
//            }
//        } catch (Exception e) {
//            log.error("Error delete pertanyaan", e);
//            return ResponseEntity.status(500).body("Error delete pertanyaan");
//        }
//    }
//
//    public PertanyaanDto findByIdPer(Long id) throws AllException {
//        log.info("Inside findbyidper");
//        Pertanyaan pertanyaan = pertanyaanRepository.findById(id).orElseThrow(() -> new AllException("pertanyaan with id" +id+"not found"));
//
//        return mapPertanyaanToPertanyaanDto(pertanyaan);
//    }
//    public PertanyaanDto mapPertanyaanToPertanyaanDto(Pertanyaan pertanyaan){
//        PertanyaanDto pertanyaanDto = new PertanyaanDto();
//        pertanyaanDto.setIdper(pertanyaan.getIdper());
//        pertanyaanDto.setKodepertanyaan(pertanyaan.getKodepertanyaan());
//        pertanyaanDto.setPertanyaan(pertanyaan.getPertanyaan());
//        pertanyaanDto.setJabatan(pertanyaan.getJabatan());
//        if (pertanyaan.getRule() != null){
//            pertanyaanDto.setKoderule(pertanyaan.getRule().getKoderule());
//        }
//
//        return pertanyaanDto;
//    }


    public PertanyaanJawabanDto findByIdPert(Long id) throws AllException {
        log.info("Inside findByIdPert");
        Pertanyaan pertanyaan = pertanyaanRepository.findById(id)
                .orElseThrow(() -> new AllException("Pertanyaan not found with ID: " + id));


        PertanyaanJawabanDto dto = new PertanyaanJawabanDto();
        dto.setIdper(pertanyaan.getIdper());
        dto.setKodepertanyaan(pertanyaan.getKodepertanyaan());
        dto.setKoderule(pertanyaan.getRule().getKoderule());
        dto.setRule(pertanyaan.getRule().getRule());
        dto.setPertanyaan(pertanyaan.getPertanyaan());
        dto.setJabatan(pertanyaan.getJabatan());


        List<JawabanDto> jawabanDtoList = pertanyaan.getJawabanList().stream()
                .map(jawaban -> {
                    JawabanDto jawabanDto = new JawabanDto();
                    jawabanDto.setIdja(jawaban.getIdja());
                    jawabanDto.setJawaban(jawaban.getJawaban());
                    jawabanDto.setBobot(jawaban.getBobot());
                    jawabanDto.setIdper(jawaban.getPertanyaan().getIdper());
                    return jawabanDto;
                })
                .collect(Collectors.toList());

        dto.setJawabanList(jawabanDtoList);

        return dto;
    }

    public ResponseEntity<Object> addPertanyaanJawabanRule(PertanyaanJawabanDto pertanyaanJawabanDto) {
        try {
            log.info("Inside addPertanyaanJawabanRule");


                Rule rules = new Rule();
                rules.setKoderule(pertanyaanJawabanDto.getKoderule());
                rules.setRule(pertanyaanJawabanDto.getRule());
                rules.setJabatan(pertanyaanJawabanDto.getJabatan());
                Rule savedRule =  ruleRepository.save(rules);

            Pertanyaan pertanyaan = new Pertanyaan();
            pertanyaan.setKodepertanyaan(pertanyaanJawabanDto.getKodepertanyaan());
            pertanyaan.setPertanyaan(pertanyaanJawabanDto.getPertanyaan());
            pertanyaan.setJabatan(pertanyaanJawabanDto.getJabatan());
            pertanyaan.setRule(savedRule);
            Pertanyaan savedPertanyaan = pertanyaanRepository.save(pertanyaan);

            for (JawabanDto jawabanDto : pertanyaanJawabanDto.getJawabanList()) {
                Jawaban jawaban = new Jawaban();
                        jawaban.setJawaban(jawabanDto.getJawaban());
                        jawaban.setBobot(jawabanDto.getBobot());
                        jawaban.setPertanyaan(savedPertanyaan);
                jawabanRepository.save(jawaban);
            }

            return ResponseEntity.ok("Rule,Pertanyaan and Jawaban added successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add Rule,Pertanyaan and Jawaban: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> editPertanyaanPertanyaanJawabanRule(Long id, PertanyaanJawabanDto pertanyaanJawabanDto) {
        try {
            log.info("Inside editPertanyaanJawabanRule");

            Optional<Rule> optionalRule = ruleRepository.findById(id);
            if (optionalRule.isPresent()) {
                Rule rule = optionalRule.get();
                rule.setRule(pertanyaanJawabanDto.getRule());
                rule.setJabatan(pertanyaanJawabanDto.getJabatan());
                ruleRepository.save(rule);
            } else {
                return ResponseEntity.notFound().build();
            }

            Optional<Pertanyaan> optionalPertanyaan = pertanyaanRepository.findById(id);
            if (optionalPertanyaan.isPresent()) {
                Pertanyaan pertanyaan = optionalPertanyaan.get();
                pertanyaan.setPertanyaan(pertanyaanJawabanDto.getPertanyaan());
                pertanyaan.setJabatan(pertanyaanJawabanDto.getJabatan());
                pertanyaanRepository.save(pertanyaan);
            } else {
                return ResponseEntity.notFound().build();
            }

            for (JawabanDto jawabanDto : pertanyaanJawabanDto.getJawabanList()) {
                Optional<Jawaban> optionalJawaban = jawabanRepository.findById(jawabanDto.getIdja());
                if (optionalJawaban.isPresent()) {
                    Jawaban jawaban = optionalJawaban.get();
                    jawaban.setJawaban(jawabanDto.getJawaban());
                    jawaban.setBobot(jawabanDto.getBobot());
                    jawabanRepository.save(jawaban);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }

            return ResponseEntity.ok("Rule, Pertanyaan, and Jawaban edited successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to edit Rule, Pertanyaan, and Jawaban: " + e.getMessage());
        }
    }

        public ResponseEntity<Object> deleteAll(Long id) {
            try {
                Optional<Pertanyaan> optionalPertanyaan = pertanyaanRepository.findById(id);

                if (optionalPertanyaan.isPresent()) {
                    pertanyaanRepository.deleteById(id);
                    return ResponseEntity.ok("Successfully deleted Rule,Pertanyaan, and jawaban");
                } else {
                    return ResponseEntity.status(404).body("not found");
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Failed to delete Rule, Pertanyaan, and Jawaban: " + e.getMessage());
            }

    }

    public Page<PertanyaanJawabanDto> showAllAndPaginationRulePertanyaanJawaban(String jabatan, String order, int offset, int pageSize) {
        log.info("Inside showAllPertanyaanPaginationByJabatan");
        Page<Pertanyaan> pertanyaanPage;

        if (jabatan == null) {
            pertanyaanPage = pertanyaanRepository.findAll(PageRequest.of(offset - 1, pageSize,  "desc".equals(order) ? Sort.by("idper").descending() : Sort.by("idper").ascending()));
        } else {
            pertanyaanPage = pertanyaanRepository.findByJabatan(jabatan,PageRequest.of(offset - 1,pageSize, "desc".equals(order) ? Sort.by("idper").descending() : Sort.by("idper").ascending()));
        }
        Page<PertanyaanJawabanDto> pertanyaanJawabanDtoPage = pertanyaanPage.map(pertanyaan -> {
            PertanyaanJawabanDto dto = new PertanyaanJawabanDto();
            dto.setIdper(pertanyaan.getIdper());
            dto.setKodepertanyaan(pertanyaan.getKodepertanyaan());
            dto.setKoderule(pertanyaan.getRule().getKoderule());
            dto.setRule(pertanyaan.getRule().getRule());
            dto.setPertanyaan(pertanyaan.getPertanyaan());
            dto.setJabatan(pertanyaan.getJabatan());

            List<JawabanDto> jawabanDtoList = pertanyaan.getJawabanList().stream()
                    .map(jawaban -> {
                        JawabanDto jawabanDto = new JawabanDto();
                        jawabanDto.setIdja(jawaban.getIdja());
                        jawabanDto.setJawaban(jawaban.getJawaban());
                        jawabanDto.setBobot(jawaban.getBobot());
                        jawabanDto.setIdper(jawaban.getPertanyaan().getIdper());
                        return jawabanDto;
                    })
                    .collect(Collectors.toList());

            dto.setJawabanList(jawabanDtoList);
            return dto;
        });

        return pertanyaanJawabanDtoPage;
    }


}
