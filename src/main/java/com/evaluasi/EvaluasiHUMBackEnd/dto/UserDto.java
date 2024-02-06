package com.evaluasi.EvaluasiHUMBackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long idkar;
    private Long iduser;
    private String kodeuser;
    private String username;
    private String password;
    private String role;
    private Boolean status;
    private Instant created;
    private String nik;

}
