package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.service.HasilEvaluasiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluasi")
public class HasilEvaluasiController {
    private final HasilEvaluasiService hasilEvaluasiService;



}
