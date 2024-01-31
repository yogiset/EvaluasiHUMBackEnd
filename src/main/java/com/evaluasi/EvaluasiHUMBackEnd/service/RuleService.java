package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.RuleDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Rule;
import com.evaluasi.EvaluasiHUMBackEnd.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleService {
    private final RuleRepository ruleRepository;

    public ResponseEntity<String> add(RuleDto ruleDto) {
        log.info("Inside Add rule");
        try{
            Rule rule = new Rule();
            rule.setKoderule(ruleDto.getKoderule());
            rule.setRule(ruleDto.getRule());
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
                .map(item -> new RuleDto(item.getIdrule(),item.getKoderule(),item.getRule()))
                .collect(Collectors.toList());
    }

}
