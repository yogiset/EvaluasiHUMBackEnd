package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.PertanyaanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.PertanyaanJawabanDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.PertanyaanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pertanyaan")
public class PertanyaanController {
    private final PertanyaanService pertanyaanService;
    
    // REQ => <url>/pertanyaan?jabatan=Sales&page=1&...
    // OR => <url>/pertanyaan
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Page<PertanyaanDto>> showAllPertanyaan(
        @RequestParam(required = false) String jabatan, // => optional
        @RequestParam(defaultValue = "desc") String order, // => optional
        @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
        @RequestParam(name = "limit", defaultValue = "5") int pageSize // => optional
        ) {
        Page<PertanyaanDto> pertanyaanDtoPage = pertanyaanService.showAllPertanyaan(jabatan, order, offset, pageSize);
        return ResponseEntity.ok(pertanyaanDtoPage);
    }

    @PostMapping(path = "/addpertanyaan")
    public ResponseEntity<Object> addPertanyaan(@RequestBody PertanyaanDto pertanyaanDto) {
        try {
            return pertanyaanService.addPertanyaan(pertanyaanDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/findbyid/{id}")
    public PertanyaanDto findById(@PathVariable Long id) throws AllException {
        return pertanyaanService.findByIdPer(id);
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

    // REQ => <url>/pertanyaan/pertanyaanjawaban?jabatan=Sales&page=1&...
    // OR => <url>/pertanyaan/pertanyaanjawaban
    @GetMapping("/pertanyaanjawaban")
    @ResponseBody
    public ResponseEntity<List<PertanyaanJawabanDto>> showAllPertanyaanJawabanPaginationByJabatan(
        @RequestParam(required = false) String jabatan, // => optional
        @RequestParam(defaultValue = "asc") String order, // => optional
        @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
        @RequestParam(name = "limit", defaultValue = "5") int pageSize // => optional
    ) {
        Page<PertanyaanJawabanDto> pertanyaanJawabanDtoPage = pertanyaanService.showAllPertanyaanJawaban(jabatan, order, offset, pageSize);

        List<PertanyaanJawabanDto> pertanyaanJawabanDtoList = pertanyaanJawabanDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pertanyaanJawabanDtoPage.getTotalElements()));

        return new ResponseEntity<>(pertanyaanJawabanDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("/findbyidperjaw/{id}")
    public PertanyaanJawabanDto findByIdpert(@PathVariable Long id) throws AllException {
        return pertanyaanService.findByIdPert(id);
    }

    @PostMapping(path = "/addall")
    public ResponseEntity<Object> addPertanyaanJawabanRule(@RequestBody PertanyaanJawabanDto pertanyaanJawabanDto) {
        try {
            return pertanyaanService.addPertanyaanJawabanRule(pertanyaanJawabanDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(path = "/editall/{id}")
    public ResponseEntity<Object>editPertanyaanJawabanRule(@PathVariable("id")Long id,@RequestBody PertanyaanJawabanDto pertanyaanJawabanDto) {
        try {
            return pertanyaanService.editPertanyaanPertanyaanJawabanRule(id,pertanyaanJawabanDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deleteall/{id}")
    public ResponseEntity<Object>deleteAll(@PathVariable("id")Long id) {
        try {
            return pertanyaanService.deleteAll(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
