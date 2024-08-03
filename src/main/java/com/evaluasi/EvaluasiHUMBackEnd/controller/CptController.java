package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.CptDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.CptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cpt")
@RequiredArgsConstructor
public class CptController {
    private final CptService cptService;

    @PostMapping(path = "/createcpt")
    public ResponseEntity<Object> createCpt(@RequestBody(required = true)CptDto cptDto, String nik) {
        try {
            return cptService.createCpt(cptDto,nik);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/editcpt/{id}")
    public ResponseEntity<Object>editCpt(@PathVariable("id")Long id,@RequestBody CptDto cptDto) {
        try {
            return cptService.editCpt(id,cptDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deletecpt/{id}")
    public ResponseEntity<Object>deleteCpt(@PathVariable("id")Long id) {
        try {
            return cptService.deleteCpt(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // REQ => <url>/cpt/showall?tahun=2023&page=1&limit=10
    // OR => <url>/cpt/showall
    @GetMapping(path = "/showall")
    @ResponseBody
    public ResponseEntity<Page<CptDto>> showAllAndPaginationCpt(
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<CptDto> cptDtoPage = cptService.showAllAndPaginationCpt(nama,tahun, order, offset, pageSize);
        return ResponseEntity.ok(cptDtoPage);
    }

    @GetMapping("/findbyid/{id}")
    public CptDto fetchCptByIdcpt(@PathVariable("id") Long id) throws AllException {
        return cptService.fetchCptDtoByIdcpt(id);
    }
}
