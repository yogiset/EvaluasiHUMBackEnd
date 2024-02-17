package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.JawabanDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.JawabanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jawaban")
public class JawabanController {
    private final JawabanService jawabanService;


    @PostMapping(path = "/addjawaban")
    public ResponseEntity<Object> addJawaban(@RequestBody JawabanDto jawabanDto) {
        try {
            return jawabanService.addJawaban(jawabanDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all")
    public List<JawabanDto> showAllJawaban(){
        return jawabanService.showAll();
    }

    @PutMapping(path = "/editjawaban/{id}")
    public ResponseEntity<Object>editJawaban(@PathVariable("id")Long id,@RequestBody JawabanDto jawabanDto) {
        try {
            return jawabanService.editJawaban(id,jawabanDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(path = "/hapusjawaban/{id}")
    public ResponseEntity<Object>hapusJawaban(@PathVariable("id")Long id) {
        try {
            return jawabanService.hapusJawaban(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/findbyid/{id}")
    public JawabanDto findById(@PathVariable Long id) throws AllException {
        return jawabanService.findByIdJawab(id);
    }
}
