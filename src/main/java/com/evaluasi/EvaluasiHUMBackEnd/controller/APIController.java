package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.service.APIResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class APIController {

    private final APIResponseBuilder apiResponseBuilder;

    @GetMapping("/data")
    public Map<String, Object> getAPIResponse() {
        return apiResponseBuilder.buildAPIResponse();
    }
}
