package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.SalesDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/sales")
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;

    @PostMapping(path = "/createdatasales")
    public ResponseEntity<Object> createSales(@RequestBody(required = true)SalesDto salesDto,String nik) {
        try {
            return salesService.createSales(salesDto,nik);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/editdatasales/{id}")
    public ResponseEntity<Object>editSales(@PathVariable("id")Long id,@RequestBody SalesDto salesDto) {
        try {
            return salesService.editSales(id,salesDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deletedatasales/{id}")
    public ResponseEntity<Object>deleteSales(@PathVariable("id")Long id) {
        try {
            return salesService.deleteSales(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // REQ => <url>/sales/showall?tahun=2022&page=1&limit=10
    // OR => <url>/sales/showall
    @GetMapping(path = "/showall")
    @ResponseBody
    public ResponseEntity<Page<SalesDto>> showAllAndPaginationSales(
            @RequestParam(required = false) Integer target, // => optional
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<SalesDto> salesDtoPage = salesService.showAllAndPaginationSales(target,tahun, order, offset, pageSize);
        return ResponseEntity.ok(salesDtoPage);
    }

    @GetMapping("/findbyid/{id}")
    public SalesDto fetchSalesByIdsales(@PathVariable("id") Long id) throws AllException {
        return salesService.fetchSalesDtoByIdsales(id);
    }

}
