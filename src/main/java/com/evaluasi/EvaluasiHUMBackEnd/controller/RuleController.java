package com.evaluasi.EvaluasiHUMBackEnd.controller;


import com.evaluasi.EvaluasiHUMBackEnd.dto.PertanyaanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.RuleDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rule")
public class RuleController {
//    private final RuleService ruleService;
//
//    @PostMapping(path = "/addrule")
//    public ResponseEntity<Object> add(@RequestBody RuleDto ruleDto) {
//        try {
//            return ruleService.add(ruleDto);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @GetMapping(path = "/all")
//    public List<RuleDto> showallrule(){
//        return ruleService.showall();
//    }
//
//    @PutMapping(path = "/editrule/{id}")
//    public ResponseEntity<Object>editRule(@PathVariable("id")Long id,@RequestBody RuleDto ruleDto) {
//        try {
//            return ruleService.editrule(id,ruleDto);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @DeleteMapping(path = "/hapusrule/{id}")
//    public ResponseEntity<Object>hapusRule(@PathVariable("id")Long id) {
//        try {
//            return ruleService.hapusRule(id);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping(path = "/findbyid/{id}")
//    public RuleDto findById(@PathVariable Long id) throws AllException {
//        return ruleService.findByIdRule(id);
//    }
//
//    @GetMapping("/rulepagination/{offset}/{pageSize}")
//    public ResponseEntity<List<RuleDto>> showAllRulePagination(@PathVariable int offset, @PathVariable int pageSize) {
//        Page<RuleDto> ruleDtoPage = ruleService.showAllRulePagination(offset, pageSize);
//
//        List<RuleDto> ruleDtoList = ruleDtoPage.getContent();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Count", String.valueOf(ruleDtoPage.getTotalElements()));
//
//        return new ResponseEntity<>(ruleDtoList, headers, HttpStatus.OK);
//    }
//    @GetMapping("/rulepaginationbyjabatan/{jabatan}/{offset}/{pageSize}")
//    public ResponseEntity<Page<RuleDto>> showAllRulePaginationByJabatan(
//            @PathVariable String jabatan,@PathVariable int offset, @PathVariable int pageSize) {
//        Page<RuleDto> ruleDtoPage = ruleService.showAllRulePaginationByJabatan(jabatan, offset, pageSize);
//        return ResponseEntity.ok(ruleDtoPage);
//    }
//    @GetMapping("/rulepaginationascjabatan/{offset}/{pageSize}")
//    public ResponseEntity<List<RuleDto>> showAllRulePaginationAscJabatan(@PathVariable int offset, @PathVariable int pageSize) {
//        Page<RuleDto> ruleDtoPage = ruleService.showAllRulePaginationAscJabatan(offset, pageSize);
//
//        List<RuleDto> ruleDtoList = ruleDtoPage.getContent();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Count", String.valueOf(ruleDtoPage.getTotalElements()));
//
//        return new ResponseEntity<>(ruleDtoList, headers, HttpStatus.OK);
//    }
//    @GetMapping("/rulepaginationdescjabatan/{offset}/{pageSize}")
//    public ResponseEntity<List<RuleDto>> showAllRulePaginationDescJabatan(@PathVariable int offset, @PathVariable int pageSize) {
//        Page<RuleDto> ruleDtoPage = ruleService.showAllRulePaginationDescJabatan(offset, pageSize);
//
//        List<RuleDto> ruleDtoList = ruleDtoPage.getContent();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Count", String.valueOf(ruleDtoPage.getTotalElements()));
//
//        return new ResponseEntity<>(ruleDtoList, headers, HttpStatus.OK);
//    }
}
