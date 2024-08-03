package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.PicosDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Picos;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.PicosService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/picos")
public class PicosController {
    private final PicosService picosService;

    @PostMapping(path = "/createpicos")
    public ResponseEntity<Object> createPicos(@RequestBody(required = true)PicosDto picosDto, String nik) {
        try {
            return picosService.createPicos(picosDto,nik);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/editpicos/{id}")
    public ResponseEntity<Object>editPicos(@PathVariable("id")Long id, @RequestBody PicosDto picosDto) {
        try {
            return picosService.editPicos(id,picosDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deletepicos/{id}")
    public ResponseEntity<Object>deletePicos(@PathVariable("id")Long id) {
        try {
            return picosService.deletePicos(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // REQ => <url>/picos/showall?tahun=2023&page=1&limit=10
    // OR => <url>/picos/showall
    @GetMapping(path = "/showall")
    @ResponseBody
    public ResponseEntity<Page<PicosDto>> showAllAndPaginationPicos(
            @RequestParam(required = false) String nama, // => optional
            @RequestParam(required = false) Integer tahun, // => optional
            @RequestParam(defaultValue = "desc") String order, // => optional
            @RequestParam(name = "page", defaultValue = "1") int offset, // => optional
            @RequestParam(name = "limit", defaultValue = "10") int pageSize // => optional
    ) {
        Page<PicosDto> picosDtoPage = picosService.showAllAndPaginationPicos(nama,tahun, order, offset, pageSize);
        return ResponseEntity.ok(picosDtoPage);
    }

    @GetMapping("/findbyid/{id}")
    public PicosDto fetchPicosByIdpicos(@PathVariable("id") Long id) throws AllException {
        return picosService.fetchPicosDtoByIdpicos(id);
    }
}
