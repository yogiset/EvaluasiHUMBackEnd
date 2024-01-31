package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.constant.ApiConstant;
import com.evaluasi.EvaluasiHUMBackEnd.dto.KaryawanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserDto;
import com.evaluasi.EvaluasiHUMBackEnd.service.KaryawanService;
import com.evaluasi.EvaluasiHUMBackEnd.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

}
