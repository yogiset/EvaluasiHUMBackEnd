package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.service.HasilEvaluasiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class HasilEvaluasiController {
    private final HasilEvaluasiService hasilEvaluasiService;

}
