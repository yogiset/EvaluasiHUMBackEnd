package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.EvaluasiDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserEvaResultDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.EvaluasiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        HttpHeaders headers = new HttpHeaders();
    // REQ => <url>/evaluasi/showall?hasil=baik&page=1&limit=10
    // OR => <url>/evaluasi/showall?tanggal=2021-11-10&page=1&limit=10
    // OR => <url>/evaluasi/showall
    @GetMapping("/showall")
    @ResponseBody
    public ResponseEntity<Page<EvaluasiDto>> showAllAndPaginationEvaluasi(
            @RequestParam(name = "hasil",required = false) String hasilevaluasi, // => optional
            @RequestParam(name = "tanggal",required = false) LocalDate tanggalevaluasi,// => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<EvaluasiDto> evaluasiDtoPage = evaluasiService.showAllAndPaginationEvaluasi(hasilevaluasi,tanggalevaluasi, order, offset, pageSize);
        return ResponseEntity.ok(evaluasiDtoPage);
    }

    @GetMapping("/findbyid/{id}")
    public EvaluasiDto findById(@PathVariable Long id) throws AllException {
        return evaluasiService.findByIdEva(id);
    }

    @GetMapping("/findbyidkar/{id}")
    public UserEvaResultDto findByIdkar(@PathVariable Long id) throws AllException {
        return evaluasiService.findByIdKar(id);
    }

    // REQ => <url>/evaluasi/showallevaluasikaryawan?hasil=baik&page=1&limit=10
    // OR => <url>/evaluasi/showallevaluasikaryawan?tanggal=2021-11-10&page=1&limit=10
    // OR => <url>/evaluasi/showallevaluasikaryawan
    @GetMapping("/showallevaluasikaryawan")
    @ResponseBody
    public ResponseEntity<Page<UserEvaResultDto>> showAllAndPaginationHasilEvaluasiKaryawan(
            @RequestParam(name = "hasil",required = false) String hasilevaluasi, // => optional
            @RequestParam(name = "tanggal",required = false) LocalDate tanggalevaluasi,// => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<UserEvaResultDto> userEvaResultDtoPage = evaluasiService.showAllAndPaginationHasilEvaluasiKaryawan(hasilevaluasi,tanggalevaluasi, order, offset, pageSize);
        return ResponseEntity.ok(userEvaResultDtoPage);
    }


//    @GetMapping("/hasilevaluasipagination/{offset}/{pageSize}")
//    public ResponseEntity<List<UserEvaResultDto>> showAllEvaluationPagination(@PathVariable int offset, @PathVariable int pageSize) {
//        Page<UserEvaResultDto> userEvaResultDtoPage = evaluasiService.showAllEvaluationWithPagination(offset, pageSize);
//
//        List<UserEvaResultDto> userEvaResultDtoList = userEvaResultDtoPage.getContent();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Count", String.valueOf(userEvaResultDtoPage.getTotalElements()));
//
//        return new ResponseEntity<>(userEvaResultDtoList, headers, HttpStatus.OK);
//    }
//    @GetMapping("/hasilevaluasipaginationbyhasil/{hasilevaluasi}/{offset}/{pageSize}")
//    public ResponseEntity<Page<UserEvaResultDto>> showAllEvaluasiPaginationByHasil(
//            @PathVariable String hasilevaluasi,@PathVariable int offset, @PathVariable int pageSize) {
//        Page<UserEvaResultDto> userEvaResultDtoPage = evaluasiService.showAllEvaluasiPaginationByHasil(hasilevaluasi, offset, pageSize);
//        return ResponseEntity.ok(userEvaResultDtoPage);
//    }
//    @GetMapping("/hasilevaluasipaginationasctanggal/{offset}/{pageSize}")
//    public ResponseEntity<List<UserEvaResultDto>> showAllEvaluationPaginationAscTanggal(@PathVariable int offset, @PathVariable int pageSize) {
//        Page<UserEvaResultDto> userEvaResultDtoPage = evaluasiService.showAllEvaluationWithPaginationAscTanggal(offset, pageSize);
//
//        List<UserEvaResultDto> userEvaResultDtoList = userEvaResultDtoPage.getContent();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Count", String.valueOf(userEvaResultDtoPage.getTotalElements()));
//
//        return new ResponseEntity<>(userEvaResultDtoList, headers, HttpStatus.OK);
//    }
//    @GetMapping("/hasilevaluasipaginationdesctanggal/{offset}/{pageSize}")
//    public ResponseEntity<List<UserEvaResultDto>> showAllEvaluationPaginationDescTanggal(@PathVariable int offset, @PathVariable int pageSize) {
//        Page<UserEvaResultDto> userEvaResultDtoPage = evaluasiService.showAllEvaluationWithPaginationDescTanggal(offset, pageSize);
//
//        List<UserEvaResultDto> userEvaResultDtoList = userEvaResultDtoPage.getContent();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Count", String.valueOf(userEvaResultDtoPage.getTotalElements()));
//
//        return new ResponseEntity<>(userEvaResultDtoList, headers, HttpStatus.OK);
//    }




    


}
