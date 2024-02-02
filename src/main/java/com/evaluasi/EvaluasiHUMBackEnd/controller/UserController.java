package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.AuthResponse;
import com.evaluasi.EvaluasiHUMBackEnd.dto.RuleDto;
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


    @PutMapping(path = "/edituser/{id}")
    public ResponseEntity<Object>editUser(@PathVariable("id")Long id,@RequestBody UserDto userDto) {
        try {
            return userService.editUser(id,userDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(path = "/hapususer/{id}")
    public ResponseEntity<Object>hapusUser(@PathVariable("id")Long id) {
        try {
            return userService.hapusUser(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object>login(@RequestBody AuthResponse authResponse){
        try {
            return userService.login(authResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
