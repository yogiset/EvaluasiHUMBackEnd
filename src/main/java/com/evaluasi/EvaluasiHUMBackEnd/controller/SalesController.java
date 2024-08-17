package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.*;
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
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<SalesDto> salesDtoPage = salesService.showAllAndPaginationSales(tahun,nama, order, offset, pageSize);
        return ResponseEntity.ok(salesDtoPage);
    }

    // OR => <url>/sales/achivementtotal
    @GetMapping(path = "/achivementtotal")
    @ResponseBody
    public ResponseEntity<Page<AchivementTotalDto>> showAllAndPaginationAchivementTotal(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<AchivementTotalDto> achivementTotalDtoPage = salesService.achivementTotalPagination(tahun,nama, order, offset, pageSize);
        return ResponseEntity.ok(achivementTotalDtoPage);
    }

    // OR => <url>/sales/achivementgadus
    @GetMapping(path = "/achivementgadus")
    @ResponseBody
    public ResponseEntity<Page<AchivementGadusDto>> showAllAndPaginationAchivementGadus(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<AchivementGadusDto> achivementGadusDtoPage = salesService.achivementGadusPagination(tahun,nama, order, offset, pageSize);
        return ResponseEntity.ok(achivementGadusDtoPage);
    }

    // OR => <url>/sales/achivementpremium
    @GetMapping(path = "/achivementpremium")
    @ResponseBody
    public ResponseEntity<Page<AchivementPremiumDto>> showAllAndPaginationAchivementPremium(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<AchivementPremiumDto> achivementPremiumDtoPage = salesService.achivementPremiumPagination(tahun,nama, order, offset, pageSize);
        return ResponseEntity.ok(achivementPremiumDtoPage);
    }

    // OR => <url>/sales/jumlahcustomerandvisit
    @GetMapping(path = "/jumlahcustomerandvisit")
    @ResponseBody
    public ResponseEntity<Page<JumlahCustomerAndVisitDto>> showAllAndPaginationJumlahCustomerAndVisit(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<JumlahCustomerAndVisitDto> jumlahCustomerAndVisitDtoPage = salesService.jumlahCustomerAndVisitPagination(tahun,nama, order, offset, pageSize);
        return ResponseEntity.ok(jumlahCustomerAndVisitDtoPage);
    }

    @GetMapping("/findbyid/{id}")
    public SalesDto fetchSalesByIdsales(@PathVariable("id") Long id) throws AllException {
        return salesService.fetchSalesDtoByIdsales(id);
    }

    @GetMapping(path = "/penilaiansales")
    @ResponseBody
    public ResponseEntity<Page<PenilaianSalesDto>> penilaianSales(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<PenilaianSalesDto> penilaianSalesDtoPage = salesService.paginationPenilaianSales(tahun,nama, order, offset, pageSize);
        return ResponseEntity.ok(penilaianSalesDtoPage);
    }

    @GetMapping(path = "/matrikskeputusan")
    @ResponseBody
    public ResponseEntity<Page<PenilaianSalesDto>> penilaianKriteriamatrikskeputusan(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<PenilaianSalesDto> penilaianSalesDtoPage = salesService.paginationPenilaianKriteria(tahun,nama, order, offset, pageSize);
        return ResponseEntity.ok(penilaianSalesDtoPage);
    }

    @GetMapping(path = "/normalisasimatrikskeputusan")
    @ResponseBody
    public ResponseEntity<Page<PenilaianSalesDto>> normalisasiMatrikskeputusan(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<PenilaianSalesDto> penilaianSalesDtoPage = salesService.normalisasiMatrikskeputusan(tahun,nama, order, offset, pageSize);
        return ResponseEntity.ok(penilaianSalesDtoPage);
    }

    @GetMapping(path = "/perangkingan")
    @ResponseBody
    public ResponseEntity<Page<RankDto>> perangkinganSales(
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<RankDto> rankDtoPage = salesService.perangkinganSales(tahun,nama, order, offset, pageSize);
        return ResponseEntity.ok(rankDtoPage);
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
        Page<PenilaianSalesBulananDto> penilaianSalesBulananDtoPage = salesService.paginationPenilaianSalesBulanan(tahun,nama,bulan,order, offset, pageSize);
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
        Page<PenilaianSalesBulananDto> penilaianSalesBulananDtoPage = salesService.paginationPenilaianKriteriaBulanan(tahun,nama,bulan, order, offset, pageSize);
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
        Page<PenilaianSalesBulananDto> penilaianSalesBulananDtoPage = salesService.normalisasiMatrikskeputusanBulanan(tahun,nama,bulan,order, offset, pageSize);
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
        Page<RankBulanDto> rankBulanDtoPage = salesService.perangkinganSalesBulanan(tahun,nama,bulan,order, offset, pageSize);
        return ResponseEntity.ok(rankBulanDtoPage);
    }

}
