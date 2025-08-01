package com.example.promise.global.code.status;


import com.example.promise.global.code.BaseErrorCode;
import com.example.promise.global.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    DATABASE_ERROR(HttpStatus.BAD_REQUEST, "COMMON404", "데이터베이스 에러가 발생하였습니다. 다시 시도해주십시오. "),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"COMMON405", "해당 Refresh Token을 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST,"COMMON406", "유효하지 않은 Refresh Token입니다."),


    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4003", "이메일이 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),
    EMAIL_FAILED(HttpStatus.BAD_REQUEST, "MEMBER4004","이메일 전송에 실패하였습니다."),
    EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST,"MEMBER4005","이메일이 이미 존재합니다."),
    NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST,"MEMBER4006","닉네임이 이미 존재합니다."),
    TYPE_NOT_FOUND(HttpStatus.NOT_FOUND,"MEMBER4007", "해당 유형이 존재하지 않습니다."),
    PASSWORD_FAILED(HttpStatus.BAD_REQUEST, "MEMBER4008","계정이 존재하지 않거나 비밀번호가 틀렸습니다."),

    CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "CHALLENGE4001","챌린지그룹이 없거나 참여 인력이 없습니다."),

    PASSWORD_VALIDATION_FAILED(HttpStatus.BAD_REQUEST,"MEMBER4009","비밀번호는 영어 대/소문자, 숫자 중 2종류 이상을 조합해야 하며 8글자에서 12글자 사이의 값이여야 합니다."),
    EMAIL_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "MEMBER4010","올바르지 않은 이메일 형식입니다."),

    JWT_SIGNATURE_INVALID(HttpStatus.UNAUTHORIZED, "AUTH001", "JWT 서명이 올바르지 않습니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH002", "JWT 토큰이 만료되었습니다."),
    JWT_MALFORMED(HttpStatus.UNAUTHORIZED, "AUTH003", "JWT 토큰이 올바르지 않은 형식입니다."),

    TOUR_API_FAIL(HttpStatus.BAD_REQUEST,"TOUR4001","TOUR API 호출에 실패하였습니다."),
    TOURIST_SPOT_NOT_FOUND(HttpStatus.NOT_FOUND,"TOURAPI4002","해당 관광지를 찾을 수 없습니다."),

    INVALID_PLAN_DATE(HttpStatus.BAD_REQUEST, "PLAN4001", "여행 시작일은 종료일보다 늦을 수 없습니다."),



    PAGE_BOUND_ERROR(HttpStatus.BAD_REQUEST, "PAGE4001", "페이징 번호가 적절하지 않습니다."),


    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTIFICATION4001", "조회할 알림 목록이 없습니다."),
    NOTIFICATION_ALREADY_READ(HttpStatus.BAD_REQUEST, "NOTIFICATION4002", "이미 읽음처리 된 알람입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE4001", "존재하지 않는 리소스입니다."),


    IMAGE_FAILED(HttpStatus.BAD_REQUEST,"IMAGE4001","이미지 올리는 것을 실패하였습니다."),

    IMAGE_TEXT_FAILD(HttpStatus.BAD_REQUEST, "IMAGETEXT4001", "이미지 텍스트 추출을 실패하였습니다.");




    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
