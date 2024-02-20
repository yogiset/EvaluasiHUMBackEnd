package com.evaluasi.EvaluasiHUMBackEnd.controller;


import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.RuleDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.KaryawanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/karyawan")
public class KaryawanController {
    private final KaryawanService karyawanService;

    @PostMapping(path = "/addkaryawan")
    public ResponseEntity<Object> addkaryawan(@RequestBody KaryawanDto karyawanDto) {
        try {
            return karyawanService.addkaryawan(karyawanDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/all")
    public List<KaryawanDto>showallkaryawan(){
        return karyawanService.showall();
    }

    @GetMapping(path = "/findbyid/{id}")
    public KaryawanDto findById(@PathVariable Long id) throws AllException {
        return karyawanService.findByIdKar(id);
    }
    @GetMapping(path = "/findbynama/{nama}")
    public KaryawanDto findByNama(@PathVariable String nama) throws AllException {
        return karyawanService.findByIdNama(nama);
    }

    @PutMapping(path = "/editkaryawan/{id}")
    public ResponseEntity<Object>editKaryawan(@PathVariable("id")Long id,@RequestBody KaryawanDto karyawanDto) {
        try {
            return karyawanService.editKaryawan(id,karyawanDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(path = "/hapuskaryawan/{id}")
    public ResponseEntity<Object>hapusKaryawan(@PathVariable("id")Long id) {
        try {
            return karyawanService.hapusKaryawan(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/karyawanpagination/{offset}/{pageSize}")
    public ResponseEntity<List<KaryawanDto>> showAllKaryawanPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<KaryawanDto> karyawanDtoPage = karyawanService.showAllKaryawanPagination(offset, pageSize);

        List<KaryawanDto> karyawanDtoList = karyawanDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(karyawanDtoPage.getTotalElements()));

        return new ResponseEntity<>(karyawanDtoList, headers, HttpStatus.OK);
    }
    @GetMapping("/karyawanpaginationbyjabatan/{jabatan}/{offset}/{pageSize}")
    public ResponseEntity<Page<KaryawanDto>> showAllKaryawanPaginationByJabatan(
            @PathVariable String jabatan,@PathVariable int offset, @PathVariable int pageSize) {
        Page<KaryawanDto> karyawanDtoPage = karyawanService.showAllKaryawanPaginationByJabatan(jabatan, offset, pageSize);
        return ResponseEntity.ok(karyawanDtoPage);
    }
    @GetMapping("/karyawanpaginationascjabatan/{offset}/{pageSize}")
    public ResponseEntity<List<KaryawanDto>> showAllKaryawanPaginationAscJabatan(@PathVariable int offset, @PathVariable int pageSize) {
        Page<KaryawanDto> karyawanDtoPage = karyawanService.showAllKaryawanPaginationAscJabatan(offset, pageSize);

        List<KaryawanDto> karyawanDtoList = karyawanDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(karyawanDtoPage.getTotalElements()));

        return new ResponseEntity<>(karyawanDtoList, headers, HttpStatus.OK);
    }
    @GetMapping("/karyawanpaginationdescjabatan/{offset}/{pageSize}")
    public ResponseEntity<List<KaryawanDto>> showAllKaryawanPaginationDescJabatan(@PathVariable int offset, @PathVariable int pageSize) {
        Page<KaryawanDto> karyawanDtoPage = karyawanService.showAllKaryawanPaginationDescJabatan(offset, pageSize);

        List<KaryawanDto> karyawanDtoList = karyawanDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(karyawanDtoPage.getTotalElements()));

        return new ResponseEntity<>(karyawanDtoList, headers, HttpStatus.OK);
    }
}
