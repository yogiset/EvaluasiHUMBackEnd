package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.BobotKriteriaDto;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.BobotKriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bobotkriteria")
public class BobotKriteriaController {

    private final BobotKriteriaService bobotKriteriaService;

    @PostMapping(path = "/createbobot")
    public ResponseEntity<Object> createBobot(@RequestBody(required = true) BobotKriteriaDto bobotKriteriaDto) {
        try {
            return bobotKriteriaService.createBobot(bobotKriteriaDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/editbobot/{id}")
    public ResponseEntity<Object>editBobot(@PathVariable("id")Long id,@RequestBody BobotKriteriaDto bobotKriteriaDto) {
        try {
            return bobotKriteriaService.editBobot(id,bobotKriteriaDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deletebobot/{id}")
    public ResponseEntity<Object>deleteBobot(@PathVariable("id")Long id) {
        try {
            return bobotKriteriaService.deleteBobot(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // REQ => <url>/bobotkriteria/showall?bobot=5&page=1&limit=10
    // OR => <url>/bobotkriteria/showall
    @GetMapping(path = "/showall")
    @ResponseBody
    public ResponseEntity<Page<BobotKriteriaDto>> showAllAndPaginationBobot(
            @RequestParam(required = false) String nmkriteria, // => optional
            @RequestParam(required = false) Integer bobot, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<BobotKriteriaDto> bobotKriteriaDtoPage = bobotKriteriaService.showAllAndPaginationBobot(nmkriteria,bobot, order, offset, pageSize);
        return ResponseEntity.ok(bobotKriteriaDtoPage);
    }

    @GetMapping("/findbyid/{id}")
    public BobotKriteriaDto fetchBobotKriteriaByIdbobot(@PathVariable("id") Long id) throws AllException {
        return bobotKriteriaService.fetchBobotKriteriaDtoByIdbobot(id);
    }
}
