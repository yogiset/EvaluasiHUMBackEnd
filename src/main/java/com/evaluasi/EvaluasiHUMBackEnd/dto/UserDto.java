package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long iduser;
    private String kodeuser;
    private String username;
    private String password;
    private String role;
    private String status;
    private Instant created;
    private Long idkar;

}
