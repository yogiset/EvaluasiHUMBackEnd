package com.evaluasi.EvaluasiHUMBackEnd.controller;


import com.evaluasi.EvaluasiHUMBackEnd.dto.*;
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
    public ResponseEntity<Object> addkaryawan(@RequestBody KaryawanDto karyawanDto,String nik) {
        try {
            return karyawanService.addkaryawan(karyawanDto,nik);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    // REQ => <url>/karyawan/showall?jabatan=Sales&page=1&limit=10
    // OR => <url>/karyawan/showall
    @GetMapping("/showall")
    @ResponseBody
    public ResponseEntity<Page<KaryawanDto>> showAllAndPaginationKaryawan(
            @RequestParam(required = false) String jabatan, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<KaryawanDto> karyawanDtoPage = karyawanService.showAllAndPaginationKaryawan(jabatan, order, offset, pageSize);
        return ResponseEntity.ok(karyawanDtoPage);
    }

    @PostMapping("/changeemail")
    public ResponseEntity<Object>changeEmail(@RequestBody ChangeEmail changeEmail){
        try {
            return karyawanService.changeEmails(changeEmail);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
