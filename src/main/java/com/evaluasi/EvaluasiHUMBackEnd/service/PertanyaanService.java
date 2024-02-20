package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.JawabanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.PertanyaanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.PertanyaanJawabanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserEvaResultDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.*;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.PertanyaanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final KaryawanRepository karyawanRepository;
    private final RuleRepository ruleRepository;

    public ResponseEntity<Object> addPertanyaan(PertanyaanDto pertanyaanDto) {
        try{
            log.info("Inside Add Pertanyaan");
            Pertanyaan pertanyaan = new Pertanyaan();
            pertanyaan.setKodepertanyaan(pertanyaanDto.getKodepertanyaan());
            pertanyaan.setPertanyaan(pertanyaanDto.getPertanyaan());
            pertanyaan.setJabatan(pertanyaanDto.getJabatan());
            Rule rule = ruleRepository.findByKoderule(pertanyaanDto.getKoderule());
            if(rule == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rule not found for koderule: " + pertanyaanDto.getKoderule());
            }
            pertanyaan.setRule(rule);
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
                    pertanyaanDto.setJabatan(tanya.getJabatan());
                    pertanyaanDto.setKoderule(tanya.getRule().getKoderule());
                    return pertanyaanDto;
                })
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> editPertanyaan(Long id, PertanyaanDto pertanyaanDto) {
        try {
            log.info("Inside edit pertanyaan");
            Optional<Pertanyaan> optionalPertanyaan = pertanyaanRepository.findById(id);
            Pertanyaan pertanyaan = optionalPertanyaan.get();

            pertanyaan.setKodepertanyaan(pertanyaanDto.getKodepertanyaan());
            pertanyaan.setPertanyaan(pertanyaanDto.getPertanyaan());
            pertanyaan.setJabatan(pertanyaanDto.getJabatan());
            Rule rule = ruleRepository.findByKoderule(pertanyaanDto.getKoderule());
            if(rule == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rule not found for koderule: " + pertanyaanDto.getKoderule());
            }
            pertanyaan.setRule(rule);

            pertanyaanRepository.save(pertanyaan);
            return ResponseEntity.ok("Pertanyaan edited successfully");

        }catch (Exception e){
            log.error("Error edited pertanyaan", e);
            return ResponseEntity.status(500).body("Error edited pertanyaan");
        }
    }

    public ResponseEntity<Object> hapusPertanyaan(Long id) {
        try {
            log.info("Inside hapus pertanyaan");
            Optional<Pertanyaan> optionalPertanyaan = pertanyaanRepository.findById(id);

            if (optionalPertanyaan.isPresent()) {
                pertanyaanRepository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted pertanyaan");
            } else {
                return ResponseEntity.status(404).body("Pertanyaan not found");
            }
        } catch (Exception e) {
            log.error("Error delete pertanyaan", e);
            return ResponseEntity.status(500).body("Error delete pertanyaan");
        }
    }

    public PertanyaanDto findByIdPer(Long id) throws AllException {
        log.info("Inside findbyidper");
        Pertanyaan pertanyaan = pertanyaanRepository.findById(id).orElseThrow(() -> new AllException("pertanyaan with id" +id+"not found"));

        return mapPertanyaanToPertanyaanDto(pertanyaan);
    }
    public PertanyaanDto mapPertanyaanToPertanyaanDto(Pertanyaan pertanyaan){
        PertanyaanDto pertanyaanDto = new PertanyaanDto();
        pertanyaanDto.setIdper(pertanyaan.getIdper());
        pertanyaanDto.setKodepertanyaan(pertanyaan.getKodepertanyaan());
        pertanyaanDto.setPertanyaan(pertanyaan.getPertanyaan());
        pertanyaanDto.setJabatan(pertanyaan.getJabatan());
        if (pertanyaan.getRule() != null){
            pertanyaanDto.setKoderule(pertanyaan.getRule().getKoderule());
        }

        return pertanyaanDto;
    }

    public Page<PertanyaanDto> showAllPertanyaanPagination(int offset, int pageSize) {
        log.info("Inside showAllPertanyaanPagination");

        Page<Pertanyaan> pertanyaanPage = pertanyaanRepository.findAll(PageRequest.of(offset, pageSize));

        List<PertanyaanDto> resultList = pertanyaanPage.getContent().stream()
                .map(pertanyaan -> {
                    PertanyaanDto pertanyaanDto = new PertanyaanDto();
                    Rule rule = pertanyaan.getRule();
                    pertanyaanDto.setIdper(pertanyaan.getIdper());
                    pertanyaanDto.setKodepertanyaan(pertanyaan.getKodepertanyaan());
                    pertanyaanDto.setPertanyaan(pertanyaan.getPertanyaan());
                    pertanyaanDto.setJabatan(pertanyaan.getJabatan());
                    pertanyaanDto.setKoderule(rule.getKoderule());
                    return pertanyaanDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, pertanyaanPage.getPageable(), pertanyaanPage.getTotalElements());
    }

    public Page<PertanyaanDto> showAllPertanyaanPaginationByJabatan(String jabatan, int offset, int pageSize) {
        log.info("Inside showAllPertanyaanPaginationByJabatan");
        Page<Pertanyaan> pertanyaanPage = pertanyaanRepository.findByJabatan(jabatan,PageRequest.of(offset, pageSize));

        List<PertanyaanDto> resultList = pertanyaanPage.getContent().stream()
                .map(pertanyaan -> {
                    PertanyaanDto pertanyaanDto = new PertanyaanDto();
                    Rule rule = pertanyaan.getRule();
                    pertanyaanDto.setIdper(pertanyaan.getIdper());
                    pertanyaanDto.setKodepertanyaan(pertanyaan.getKodepertanyaan());
                    pertanyaanDto.setPertanyaan(pertanyaan.getPertanyaan());
                    pertanyaanDto.setJabatan(pertanyaan.getJabatan());
                    pertanyaanDto.setKoderule(rule.getKoderule());
                    return pertanyaanDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, pertanyaanPage.getPageable(), pertanyaanPage.getTotalElements());
    }

    public Page<PertanyaanDto> showAllPertanyaanPaginationAscJabatan(int offset, int pageSize) {
        log.info("Inside showAllJawabanPaginationAscJabatan");

        Page<Pertanyaan> pertanyaanPage = pertanyaanRepository.findAll(PageRequest.of(offset, pageSize, Sort.by("jabatan").ascending()));

        List<PertanyaanDto> resultList = pertanyaanPage.getContent().stream()
                .map(pertanyaan -> {
                    PertanyaanDto pertanyaanDto = new PertanyaanDto();
                    Rule rule = pertanyaan.getRule();
                    pertanyaanDto.setIdper(pertanyaan.getIdper());
                    pertanyaanDto.setKodepertanyaan(pertanyaan.getKodepertanyaan());
                    pertanyaanDto.setPertanyaan(pertanyaan.getPertanyaan());
                    pertanyaanDto.setJabatan(pertanyaan.getJabatan());
                    pertanyaanDto.setKoderule(rule.getKoderule());
                    return pertanyaanDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, pertanyaanPage.getPageable(), pertanyaanPage.getTotalElements());
    }

    public Page<PertanyaanDto> showAllPertanyaanPaginationDescJabatan(int offset, int pageSize) {
        log.info("Inside showAllJawabanPaginationDescJabatan");

        Page<Pertanyaan> pertanyaanPage = pertanyaanRepository.findAll(PageRequest.of(offset, pageSize, Sort.by("jabatan").descending()));

        List<PertanyaanDto> resultList = pertanyaanPage.getContent().stream()
                .map(pertanyaan -> {
                    PertanyaanDto pertanyaanDto = new PertanyaanDto();
                    Rule rule = pertanyaan.getRule();
                    pertanyaanDto.setIdper(pertanyaan.getIdper());
                    pertanyaanDto.setKodepertanyaan(pertanyaan.getKodepertanyaan());
                    pertanyaanDto.setPertanyaan(pertanyaan.getPertanyaan());
                    pertanyaanDto.setJabatan(pertanyaan.getJabatan());
                    pertanyaanDto.setKoderule(rule.getKoderule());
                    return pertanyaanDto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, pertanyaanPage.getPageable(), pertanyaanPage.getTotalElements());
    }

    public Page<PertanyaanJawabanDto> showAllPertanyaanJawabanPagination(int offset, int pageSize) {
        log.info("Inside showAllPertanyaanJawabanPagination");

        Page<Pertanyaan> pertanyaanPage = pertanyaanRepository.findAll(PageRequest.of(offset, pageSize));

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

    public Page<PertanyaanJawabanDto> showAllPertanyaanJawabanPaginationAscJabatan(int offset, int pageSize) {
        log.info("Inside showAllPertanyaanJawabanPaginationAscJabatan");

        Page<Pertanyaan> pertanyaanPage = pertanyaanRepository.findAll(PageRequest.of(offset, pageSize,Sort.by("jabatan").ascending()));

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

    public Page<PertanyaanJawabanDto> showAllPertanyaanJawabanPaginationDescJabatan(int offset, int pageSize) {
        log.info("Inside showAllPertanyaanJawabanPaginationDescJabatan");

        Page<Pertanyaan> pertanyaanPage = pertanyaanRepository.findAll(PageRequest.of(offset, pageSize,Sort.by("jabatan").descending()));

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

    public Page<PertanyaanJawabanDto> showAllPertanyaanJawabanPaginationByJabatan(String jabatan, int offset, int pageSize) {
        log.info("Inside showAllPertanyaanJawabanPaginationByJabatan");

        Page<Pertanyaan> pertanyaanPage = pertanyaanRepository.findByJabatan(jabatan,PageRequest.of(offset,pageSize));

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

    public PertanyaanJawabanDto findByIdPert(Long id) throws AllException {
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
}
