package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.SalesDetailDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.SalesDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.SalesDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/salesdetail")
public class SalesDetailController {
    private final SalesDetailService salesDetailService;

    @PostMapping(path = "/createsalesdetail")
    public ResponseEntity<Object> createSalesDetail(@RequestBody(required = true)SalesDetailDto salesDetailDto) {
        try {
            return salesDetailService.createSalesDetail(salesDetailDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/editsalesdetail/{id}")
    public ResponseEntity<Object>editSalesDetail(@PathVariable("id")Long id,@RequestBody SalesDetailDto salesDetailDto) {
        try {
            return salesDetailService.editSalesDetail(id,salesDetailDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deletesalesdetail/{id}")
    public ResponseEntity<Object>deleteSalesDetail(@PathVariable("id")Long id) {
        try {
            return salesDetailService.deleteSalesDetail(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findbyid/{id}")
    public SalesDetailDto fetchSalesDetailDtoById(@PathVariable("id") Long id) throws AllException {
        return salesDetailService.fetchSalesDetailDtoById(id);

    }
}
