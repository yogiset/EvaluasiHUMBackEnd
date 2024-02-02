package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.PertanyaanDto;
import com.evaluasi.EvaluasiHUMBackEnd.service.PertanyaanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pertanyaan")
public class PertanyaanController {
    private final PertanyaanService pertanyaanService;

    @PostMapping(path = "/addpertanyaan")
    public ResponseEntity<Object> addPertanyaan(@RequestBody PertanyaanDto pertanyaanDto) {
        try {
            return pertanyaanService.addPertanyaan(pertanyaanDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all")
    public List<PertanyaanDto> showAllPertanyaan(){
        return pertanyaanService.showAll();
    }

    @PutMapping(path = "/editpertanyaan/{id}")
    public ResponseEntity<Object>editPertanyaan(@PathVariable("id")Long id,@RequestBody PertanyaanDto pertanyaanDto) {
        try {
            return pertanyaanService.editPertanyaan(id,pertanyaanDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(path = "/hapuspertanyaan/{id}")
    public ResponseEntity<Object>hapusPertanyaan(@PathVariable("id")Long id) {
        try {
            return pertanyaanService.hapusPertanyaan(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
