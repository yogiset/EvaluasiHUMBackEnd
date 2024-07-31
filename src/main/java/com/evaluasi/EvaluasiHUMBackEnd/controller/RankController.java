package com.evaluasi.EvaluasiHUMBackEnd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluasi.EvaluasiHUMBackEnd.service.RankService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rank")
public class RankController {

    private final RankService rankService;
    
}
