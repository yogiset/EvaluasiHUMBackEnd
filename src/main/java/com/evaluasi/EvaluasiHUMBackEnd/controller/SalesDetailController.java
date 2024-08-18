package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.PenilaianSalesBulananDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.RankBulanDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.SalesDetailDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.SalesDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.SalesDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping(path = "/penilaiansalesbulanan")
    @ResponseBody
    public ResponseEntity<Page<PenilaianSalesBulananDto>> penilaianSalesBulanan(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(required = false) String bulan, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<PenilaianSalesBulananDto> penilaianSalesBulananDtoPage = salesDetailService.paginationPenilaianSalesBulanan(tahun,nama,bulan,order, offset, pageSize);
        return ResponseEntity.ok(penilaianSalesBulananDtoPage);
    }

    @GetMapping(path = "/matrikskeputusanbulanan")
    @ResponseBody
    public ResponseEntity<Page<PenilaianSalesBulananDto>> penilaianKriteriamatrikskeputusanBulanan(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(required = false) String bulan, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<PenilaianSalesBulananDto> penilaianSalesBulananDtoPage = salesDetailService.paginationPenilaianKriteriaBulanan(tahun,nama,bulan, order, offset, pageSize);
        return ResponseEntity.ok(penilaianSalesBulananDtoPage);
    }

    @GetMapping(path = "/normalisasimatrikskeputusanbulanan")
    @ResponseBody
    public ResponseEntity<Page<PenilaianSalesBulananDto>> normalisasiMatrikskeputusanBulanan(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(required = false) String bulan, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<PenilaianSalesBulananDto> penilaianSalesBulananDtoPage = salesDetailService.normalisasiMatrikskeputusanBulanan(tahun,nama,bulan,order, offset, pageSize);
        return ResponseEntity.ok(penilaianSalesBulananDtoPage);
    }

    @GetMapping(path = "/perangkinganbulananan")
    @ResponseBody
    public ResponseEntity<Page<RankBulanDto>> perangkinganSales(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(required = false) String bulan, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<RankBulanDto> rankBulanDtoPage = salesDetailService.perangkinganSalesBulanan(tahun,nama,bulan,order, offset, pageSize);
        return ResponseEntity.ok(rankBulanDtoPage);
    }
}
