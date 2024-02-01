package com.evaluasi.EvaluasiHUMBackEnd.controller;


import com.evaluasi.EvaluasiHUMBackEnd.dto.RuleDto;
import com.evaluasi.EvaluasiHUMBackEnd.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rule")
public class RuleController {
    private final RuleService ruleService;

    @PostMapping(path = "/addrule")
    public ResponseEntity<Object> add(@RequestBody RuleDto ruleDto) {
        try {
            return ruleService.add(ruleDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/all")
    public List<RuleDto> showallrule(){
        return ruleService.showall();
    }
}
