package com.evaluasi.EvaluasiHUMBackEnd.service;

import org.springframework.stereotype.Service;

import com.evaluasi.EvaluasiHUMBackEnd.repository.RankRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankService {

    private final RankRepository rankRepository;
    
}
