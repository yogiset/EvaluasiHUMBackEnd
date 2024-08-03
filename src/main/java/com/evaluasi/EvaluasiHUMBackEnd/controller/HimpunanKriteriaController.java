package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.HimpunanKriteriaDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.HimpunanKriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/kriteria")
public class HimpunanKriteriaController {

    private final HimpunanKriteriaService himpunanKriteriaService;

    @PostMapping(path = "/createhim")
    public ResponseEntity<Object> createHim(@RequestBody(required = true)HimpunanKriteriaDto himpunanKriteriaDto) {
        try {
            return himpunanKriteriaService.createHim(himpunanKriteriaDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/edithim/{id}")
    public ResponseEntity<Object>editHim(@PathVariable("id")Long id,@RequestBody HimpunanKriteriaDto himpunanKriteriaDto) {
        try {
            return himpunanKriteriaService.editHim(id,himpunanKriteriaDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deletehim/{id}")
    public ResponseEntity<Object>deleteHim(@PathVariable("id")Long id) {
        try {
            return himpunanKriteriaService.deleteHim(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // REQ => <url>/kriteria/showall?nilai=5&page=1&limit=10
    // OR => <url>/kriteria/showall
    @GetMapping(path = "/showall")
    @ResponseBody
    public ResponseEntity<Page<HimpunanKriteriaDto>> showAllAndPaginationHim(
            @RequestParam(required = false) String keterangan, // => optional
            @RequestParam(required = false) String nmkriteria, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<HimpunanKriteriaDto> himpunanKriteriaDtoPage = himpunanKriteriaService.showAllAndPaginationHim(keterangan,nmkriteria, order, offset, pageSize);
        return ResponseEntity.ok(himpunanKriteriaDtoPage);
    }

    @GetMapping("/findbyid/{id}")
    public HimpunanKriteriaDto fetchHimpunanKriteriaByIdhim(@PathVariable("id") Long id) throws AllException {
        return himpunanKriteriaService.fetchHimpunanKriteriaDtoByIdhim(id);
    }

}
