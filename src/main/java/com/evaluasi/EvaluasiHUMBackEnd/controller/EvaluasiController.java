package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.EvaluasiDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserEvaResultDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.EvaluasiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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

    @PutMapping(path = "/editevaluasi/{id}")
    public ResponseEntity<Object>editHasilEvaluasi(@PathVariable("id")Long id,@RequestBody EvaluasiDto evaluasiDto) {
        try {
            return evaluasiService.editEvaluasi(id,evaluasiDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(path = "/hapusevaluasi/{id}")
    public ResponseEntity<Object>hapusEvaluasi(@PathVariable("id")Long id) {
        try {
            return evaluasiService.hapusEvaluasi(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/evaluasipagination/{offset}/{pageSize}")
    public ResponseEntity<List<EvaluasiDto>> showAllEvaPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<EvaluasiDto> evaluasiDtoPage = evaluasiService.showAllEvaWithPagination(offset, pageSize);

        List<EvaluasiDto> evaluasiDtoList = evaluasiDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(evaluasiDtoPage.getTotalElements()));

        return new ResponseEntity<>(evaluasiDtoList, headers, HttpStatus.OK);
    }
    @GetMapping("/evaluasipaginationbyhasil/{hasilevaluasi}/{offset}/{pageSize}")
    public ResponseEntity<Page<EvaluasiDto>> showAllEvaPaginationByHasil(
            @PathVariable String hasilevaluasi,@PathVariable int offset, @PathVariable int pageSize) {
        Page<EvaluasiDto> evaluasiDtoPage = evaluasiService.showAllEvaPaginationByHasil(hasilevaluasi, offset, pageSize);
        return ResponseEntity.ok(evaluasiDtoPage);
    }
    @GetMapping("/evaluasipaginationasctanggal/{offset}/{pageSize}")
    public ResponseEntity<List<EvaluasiDto>> showAllEvaPaginationAscTanggal(@PathVariable int offset, @PathVariable int pageSize) {
        Page<EvaluasiDto> evaluasiDtoPage = evaluasiService.showAllEvaWithPaginationAscTanggal(offset, pageSize);

        List<EvaluasiDto> evaluasiDtoList = evaluasiDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(evaluasiDtoPage.getTotalElements()));

        return new ResponseEntity<>(evaluasiDtoList, headers, HttpStatus.OK);
    }
    @GetMapping("/evaluasipaginationdesctanggal/{offset}/{pageSize}")
    public ResponseEntity<List<EvaluasiDto>> showAllEvaPaginationDescTanggal(@PathVariable int offset, @PathVariable int pageSize) {
        Page<EvaluasiDto> evaluasiDtoPage = evaluasiService.showAllEvaWithPaginationDescTanggal(offset, pageSize);

        List<EvaluasiDto> evaluasiDtoList = evaluasiDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(evaluasiDtoPage.getTotalElements()));

        return new ResponseEntity<>(evaluasiDtoList, headers, HttpStatus.OK);
    }


    @GetMapping("/findbyid/{id}")
    public EvaluasiDto findById(@PathVariable Long id) throws AllException {
        return evaluasiService.findByIdEva(id);
    }

    @GetMapping("/findbyidkar/{id}")
    public UserEvaResultDto findByIdkar(@PathVariable Long id) throws AllException {
        return evaluasiService.findByIdKar(id);
    }

    @GetMapping("/hasilevaluasipagination/{offset}/{pageSize}")
    public ResponseEntity<List<UserEvaResultDto>> showAllEvaluationPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<UserEvaResultDto> userEvaResultDtoPage = evaluasiService.showAllEvaluationWithPagination(offset, pageSize);

        List<UserEvaResultDto> userEvaResultDtoList = userEvaResultDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(userEvaResultDtoPage.getTotalElements()));

        return new ResponseEntity<>(userEvaResultDtoList, headers, HttpStatus.OK);
    }
    @GetMapping("/hasilevaluasipaginationbyhasil/{hasilevaluasi}/{offset}/{pageSize}")
    public ResponseEntity<Page<UserEvaResultDto>> showAllEvaluasiPaginationByHasil(
            @PathVariable String hasilevaluasi,@PathVariable int offset, @PathVariable int pageSize) {
        Page<UserEvaResultDto> userEvaResultDtoPage = evaluasiService.showAllEvaluasiPaginationByHasil(hasilevaluasi, offset, pageSize);
        return ResponseEntity.ok(userEvaResultDtoPage);
    }
    @GetMapping("/hasilevaluasipaginationasctanggal/{offset}/{pageSize}")
    public ResponseEntity<List<UserEvaResultDto>> showAllEvaluationPaginationAscTanggal(@PathVariable int offset, @PathVariable int pageSize) {
        Page<UserEvaResultDto> userEvaResultDtoPage = evaluasiService.showAllEvaluationWithPaginationAscTanggal(offset, pageSize);

        List<UserEvaResultDto> userEvaResultDtoList = userEvaResultDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(userEvaResultDtoPage.getTotalElements()));

        return new ResponseEntity<>(userEvaResultDtoList, headers, HttpStatus.OK);
    }
    @GetMapping("/hasilevaluasipaginationdesctanggal/{offset}/{pageSize}")
    public ResponseEntity<List<UserEvaResultDto>> showAllEvaluationPaginationDescTanggal(@PathVariable int offset, @PathVariable int pageSize) {
        Page<UserEvaResultDto> userEvaResultDtoPage = evaluasiService.showAllEvaluationWithPaginationDescTanggal(offset, pageSize);

        List<UserEvaResultDto> userEvaResultDtoList = userEvaResultDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(userEvaResultDtoPage.getTotalElements()));

        return new ResponseEntity<>(userEvaResultDtoList, headers, HttpStatus.OK);
    }

    


}
