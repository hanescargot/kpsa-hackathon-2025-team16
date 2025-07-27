package com.example.promise.domain.user.controller;


import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.user.converter.AuthConverter;
import com.example.promise.domain.user.dto.AuthRequestDTO;
import com.example.promise.domain.user.dto.AuthResponseDTO;
import com.example.promise.domain.user.entity.NormalUser;
import com.example.promise.domain.user.entity.status.UserRole;
import com.example.promise.domain.user.service.AuthService;
import com.example.promise.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/user-signup")
    @Operation(summary = "사용자 회원가입", description = "일반 사용자 회원가입 API")
    public ApiResponse<AuthResponseDTO.SignResponseDTO> userSignup(@RequestBody AuthRequestDTO.UserSignupDTO dto) {
        NormalUser user = AuthConverter.toUser(dto);
        return ApiResponse.onSuccess(authService.signUpUser(user));
    }

    @PostMapping("/pharmacist-signup")
    @Operation(summary = "약사 회원가입", description = "약사 회원가입 API (약국정보 포함)")
    public ApiResponse<AuthResponseDTO.SignResponseDTO> pharmacistSignup(@RequestBody AuthRequestDTO.PharmacistSignupDTO dto) {
        return ApiResponse.onSuccess(authService.signUpPharmacist(dto));
    }



    @PostMapping("/login")
    @Operation(summary = "사용자 로그인 API", description = "사용자가 이메일과 비밀번호를 사용하여 로그인합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<AuthResponseDTO.LoginResponseDTO> login(@RequestBody AuthRequestDTO.LoginRequestDTO loginRequestDTO) {
        return ApiResponse.onSuccess(authService.login(loginRequestDTO));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Access 토큰 재발급 API", description = "만료된 access 토큰을 새로 발급합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "Authorization", description = "Bearer access 토큰", required = true)
    })
    public ApiResponse<Map<String, String>> refreshToken(@RequestHeader("Authorization") String token) {

        String newToken = authService.refreshToken(token);

        // 응답 데이터 구성
        Map<String, String> response = new HashMap<>();
        response.put("token", newToken);

        return ApiResponse.onSuccess(response);
    }


}
