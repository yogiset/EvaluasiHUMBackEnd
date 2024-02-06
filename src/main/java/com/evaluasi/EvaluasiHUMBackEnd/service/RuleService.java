package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.RuleDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Evaluasi;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Rule;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.repository.EvaluasiRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleService {
    private final RuleRepository ruleRepository;
    private final EvaluasiRepository evaluasiRepository;

    public ResponseEntity<Object> add(RuleDto ruleDto) {
        log.info("Inside Add rule");
        try{
            Rule rule = new Rule();
            rule.setKoderule(ruleDto.getKoderule());
            rule.setRule(ruleDto.getRule());

            Evaluasi evaluasi = evaluasiRepository.findByKodeevaluasi(ruleDto.getKodeevaluasi());
            if (evaluasi == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evaluasi not found for Kode evaluasi: " + ruleDto.getKodeevaluasi());
            }

            rule.setEvaluasi(evaluasi);
            ruleRepository.save(rule);

            return ResponseEntity.ok("New rule added successfully");
        } catch (Exception e) {
            log.error("Error creating new rule", e);
            return ResponseEntity.status(500).body("Error creating new rule");
        }
    }


    public List<RuleDto> showall() {
        log.info("Inside Show Rule");
        List<Rule>ruleList= ruleRepository.findAll();

        return ruleList.stream()
                .map(rule -> {
                    RuleDto ruleDto = new RuleDto();
                    ruleDto.setIdrule(rule.getIdrule());
                    ruleDto.setKoderule(rule.getKoderule());
                    ruleDto.setRule(rule.getRule());
                    ruleDto.setKodeevaluasi(rule.getEvaluasi().getKodeevaluasi());
                    return ruleDto;
                })
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> editrule(Long id, RuleDto ruleDto) {
        try {
            log.info("Inside edit pertanyaan");
            Optional<Rule> optionalRule = ruleRepository.findById(id);
            Rule rule = optionalRule.get();

            rule.setKoderule(ruleDto.getKoderule());
            rule.setRule(ruleDto.getRule());

            ruleRepository.save(rule);
            return ResponseEntity.ok("Rule edited successfully");

        }catch (Exception e){
            log.error("Error edited rule", e);
            return ResponseEntity.status(500).body("Error edited rule");
        }
    }
    public ResponseEntity<Object> hapusRule(Long id) {
        try {
            log.info("Inside hapus rule");
            Optional<Rule> optionalRule = ruleRepository.findById(id);

            if (optionalRule.isPresent()) {
                ruleRepository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted rule");
            } else {
                return ResponseEntity.status(404).body("rule not found");
            }
        } catch (Exception e) {
            log.error("Error delete rule", e);
            return ResponseEntity.status(500).body("Error delete rule");
        }
     }

    public RuleDto findByIdRule(Long id) throws AllException {
        log.info("Inside find by id rule");
        Rule rule = ruleRepository.findById(id).orElseThrow(() -> new AllException("Rule with id " + id + " not found"));

        return mapRuleToRuleDto(rule);
    }
    public RuleDto mapRuleToRuleDto(Rule rule){
        RuleDto ruleDto = new RuleDto();
        ruleDto.setIdrule(rule.getIdrule());
        ruleDto.setKoderule(rule.getKoderule());
        ruleDto.setRule(rule.getRule());
        if(rule.getEvaluasi() != null){
            ruleDto.setKodeevaluasi(rule.getEvaluasi().getKodeevaluasi());
        }

        return ruleDto;
    }

}


