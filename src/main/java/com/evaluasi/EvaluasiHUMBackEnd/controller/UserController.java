package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.UserDto;
import com.evaluasi.EvaluasiHUMBackEnd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/createaccount")
    public ResponseEntity<Object> createAccount(@RequestBody(required = true)UserDto userDto) {
        try {
            return userService.createAccount(userDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/all")
    public List<UserDto> showAlluser(){
        return userService.showall();
    }
}
