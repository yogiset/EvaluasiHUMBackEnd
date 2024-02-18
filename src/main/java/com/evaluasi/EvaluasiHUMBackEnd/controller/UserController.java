package com.evaluasi.EvaluasiHUMBackEnd.controller;

import com.evaluasi.EvaluasiHUMBackEnd.dto.*;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @GetMapping("/findById/{id}")
    public UserDto fetchUserByIduser(@PathVariable("id") Long id) throws AllException {
        return userService.fetchUserDtoByIduser(id);
    }

    @GetMapping("/userresult")
    public ResponseEntity<UserEvaResultDto> getUserInfoByUsername(@RequestParam String username,UserEvaResultDto userEvaResultDtoo,AuthResponse authResponse) {
        try {
            UserEvaResultDto userEvaResultDto = userService.getUserEvaResultByUsername(username,userEvaResultDtoo,authResponse);
            return new ResponseEntity<>(userEvaResultDto, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/changepassword")
    public ResponseEntity<Object>changePassword(@RequestBody ChangePassword changePassword){
        try {
            return userService.changePasswords(changePassword);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/changeusername")
    public ResponseEntity<Object>changeUser(@RequestBody ChangeUsername changeUsername){
        try {
            return userService.changeUsername(changeUsername);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/findByUsername/{username}")
    public UserDto fetchUserByUsername(@PathVariable("username") String username) throws AllException {
        return userService.fetchUserDtoByUsername(username);
    }
    @GetMapping("/userpagination/{offset}/{pageSize}")
    public ResponseEntity<List<UserDto>> showAllUserPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<UserDto> userDtoPage = userService.showAllUserPagination(offset, pageSize);

        List<UserDto> userDtoList = userDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(userDtoPage.getTotalElements()));

        return new ResponseEntity<>(userDtoList, headers, HttpStatus.OK);
    }
    @GetMapping("/userpaginationascusername/{offset}/{pageSize}")
    public ResponseEntity<List<UserDto>> showAllUserPaginationAscUsername(@PathVariable int offset, @PathVariable int pageSize) {
        Page<UserDto> userDtoPage = userService.showAllUserPaginationAscUsername(offset, pageSize);

        List<UserDto> userDtoList = userDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(userDtoPage.getTotalElements()));

        return new ResponseEntity<>(userDtoList, headers, HttpStatus.OK);
    }
    @GetMapping("/userpaginationdescusername/{offset}/{pageSize}")
    public ResponseEntity<List<UserDto>> showAllUserPaginationDescUsername(@PathVariable int offset, @PathVariable int pageSize) {
        Page<UserDto> userDtoPage = userService.showAllUserPaginationDescUsername(offset, pageSize);

        List<UserDto> userDtoList = userDtoPage.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(userDtoPage.getTotalElements()));

        return new ResponseEntity<>(userDtoList, headers, HttpStatus.OK);
    }

}
