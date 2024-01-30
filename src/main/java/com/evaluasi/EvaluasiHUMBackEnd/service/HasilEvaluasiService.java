package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.repository.HasilEvaluasiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HasilEvaluasiService {
    private final HasilEvaluasiRepository hasilEvaluasiRepository;

}
