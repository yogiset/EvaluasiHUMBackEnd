package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.EvaluasiDto;
import com.evaluasi.EvaluasiHUMBackEnd.service.EvaluasiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluasi")
public class EvaluasiController {
    private final EvaluasiService evaluasiService;

    @PostMapping(path = "/addevaluasi")
    public ResponseEntity<Object> addHasilEvaluasi(@RequestBody EvaluasiDto evaluasiDto) {
        try {
            return evaluasiService.addEvaluasi(evaluasiDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all")
    public List<EvaluasiDto> showAllEvaluasi(){
        return evaluasiService.showAll();
    }



}