package com.example.promise.domain.mypage.controller;

import com.example.promise.domain.mypage.dto.PointHistoryDto;
import com.example.promise.domain.mypage.dto.UserInfoDto;
import com.example.promise.domain.mypage.dto.UserUpdateRequestDto;
import com.example.promise.domain.mypage.dto.GuardianRequestDto;
import com.example.promise.domain.mypage.service.MyPageService;
import com.example.promise.global.auth.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/point-history")
    @Operation(summary = "포인트 적립 내역 조회", description = "유저의 챌린지 기반 포인트 적립 내역을 조회합니다.")
    public List<PointHistoryDto> getPointHistory(@Parameter(hidden = true) @AuthUser Long userId) {
        return myPageService.getPointHistory(userId);
    }

    @GetMapping("/user")
    @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 정보를 조회합니다.")
    public UserInfoDto getMyInfo(@Parameter(hidden = true) @AuthUser Long userId) {
        return myPageService.getMyInfo(userId);
    }

    @PatchMapping("/user")
    @Operation(summary = "내 정보 수정", description = "로그인한 사용자의 정보를 수정합니다.")
    public void updateMyInfo(@Parameter(hidden = true) @AuthUser Long userId,
                             @RequestBody UserUpdateRequestDto request) {
        myPageService.updateMyInfo(userId, request);
    }

    @PostMapping("/guardian")
    @Operation(summary = "보호자 등록", description = "사용자의 보호자 정보를 등록합니다.")
    public void registerGuardian(@Parameter(hidden = true) @AuthUser Long userId,
                                 @RequestBody GuardianRequestDto request) {
        myPageService.registerGuardian(userId, request);
    }

    @PatchMapping("/guardian")
    @Operation(summary = "보호자 수정", description = "사용자의 보호자 정보를 수정합니다.")
    public void updateGuardian(@Parameter(hidden = true) @AuthUser Long userId,
                               @RequestBody GuardianRequestDto request) {
        myPageService.updateGuardian(userId, request);
    }
}