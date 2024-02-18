package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.PertanyaanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserEvaResultDto;
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

    @GetMapping("/pertanyaanpagination/{offset}/{pageSize}")
    public ResponseEntity<List<PertanyaanDto>> showAllPertanyaanPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<PertanyaanDto> pertanyaanDtoPage = pertanyaanService.showAllPertanyaanPagination(offset, pageSize);

        List<PertanyaanDto> pertanyaanDtoList = pertanyaanDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pertanyaanDtoPage.getTotalElements()));

        return new ResponseEntity<>(pertanyaanDtoList, headers, HttpStatus.OK);
    }
    @GetMapping("/pertanyaanpaginationbyjabatan/{jabatan}/{offset}/{pageSize}")
    public ResponseEntity<Page<PertanyaanDto>> showAllPertanyaanPaginationByJabatan(
            @PathVariable String jabatan,@PathVariable int offset, @PathVariable int pageSize) {
        Page<PertanyaanDto> pertanyaanDtoPage = pertanyaanService.showAllPertanyaanPaginationByJabatan(jabatan, offset, pageSize);
        return ResponseEntity.ok(pertanyaanDtoPage);
    }
    @GetMapping("/pertanyaanpaginationascjabatan/{offset}/{pageSize}")
    public ResponseEntity<List<PertanyaanDto>> showAllPertanyaanPaginationAscJabatan(@PathVariable int offset, @PathVariable int pageSize) {
        Page<PertanyaanDto> pertanyaanDtoPage = pertanyaanService.showAllPertanyaanPaginationAscJabatan(offset, pageSize);

        List<PertanyaanDto> pertanyaanDtoList = pertanyaanDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pertanyaanDtoPage.getTotalElements()));

        return new ResponseEntity<>(pertanyaanDtoList, headers, HttpStatus.OK);
    }
    @GetMapping("/pertanyaanpaginationdescjabatan/{offset}/{pageSize}")
    public ResponseEntity<List<PertanyaanDto>> showAllPertanyaanPaginationDescJabatan(@PathVariable int offset, @PathVariable int pageSize) {
        Page<PertanyaanDto> pertanyaanDtoPage = pertanyaanService.showAllPertanyaanPaginationDescJabatan(offset, pageSize);

        List<PertanyaanDto> pertanyaanDtoList = pertanyaanDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pertanyaanDtoPage.getTotalElements()));

        return new ResponseEntity<>(pertanyaanDtoList, headers, HttpStatus.OK);
    }
}
