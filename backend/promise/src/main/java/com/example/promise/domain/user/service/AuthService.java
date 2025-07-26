package com.example.promise.domain.user.service;



import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.pharmacist.repository.PharmacistRepository;
import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.pharmacy.repository.PharmacyRepository;
import com.example.promise.domain.pharmacy.service.PharmacyApiService;
import com.example.promise.domain.user.converter.AuthConverter;
import com.example.promise.domain.user.dto.AuthRequestDTO;
import com.example.promise.domain.user.dto.AuthResponseDTO;
import com.example.promise.domain.user.entity.NormalUser;
import com.example.promise.domain.user.repository.NormalUserRepository;
import com.example.promise.global.code.status.ErrorStatus;
import com.example.promise.global.exception.GeneralException;
import com.example.promise.global.security.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final NormalUserRepository normalUserRepository;
    private final PharmacistRepository pharmacistRepository;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final PharmacyRepository pharmacyRepository;
    private final PharmacyApiService pharmacyApiService;

    public AuthResponseDTO.SignResponseDTO signUpUser(NormalUser user) {
        validatePassword(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        normalUserRepository.save(user);
        return AuthConverter.toSigninResponseDTO(user);
    }

    public AuthResponseDTO.SignResponseDTO signUpPharmacist(AuthRequestDTO.PharmacistSignupDTO dto) {
        validatePassword(dto.getPassword());

        // 1. 약사 객체 생성
        Pharmacist pharmacist = Pharmacist.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .phone(dto.getPhone())
                .birthDate(dto.getBirthDate())
                .pharmacyVerify(dto.getPharmacyVerify())
                .build();

        // 2. 약국 이름으로 API 조회
        List<Pharmacy> pharmacies = pharmacyApiService.fetchAndSavePharmacyList(dto.getPharmacyName());
        if (pharmacies.isEmpty()) {
            throw new RuntimeException("해당 이름의 약국 정보를 찾을 수 없습니다.");
        }

        // 3. 첫 번째 약국을 자동 매핑 (or 프론트에서 선택하게 변경 가능)
        Pharmacy pharmacy = pharmacies.get(0);

        // 4. 연관관계 매핑
        pharmacist.setPharmacy(pharmacy);

        // 5. 저장
        pharmacistRepository.save(pharmacist);

        // 6. 반환
        return AuthConverter.toSigninResponseDTO(pharmacist);
    }





    public void validatePassword(String password) {
        // 길이 검사
        if (password.length() < 8 || password.length() > 12) {
            throw new GeneralException(ErrorStatus.PASSWORD_VALIDATION_FAILED);
        }

        // 영어 대문자, 소문자, 숫자 포함 여부 검사
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        // 최소 2종류 이상 포함 여부 확인
        int count = 0;
        if (hasUpperCase) count++;
        if (hasLowerCase) count++;
        if (hasDigit) count++;

        if (count < 2) {
            throw new GeneralException(ErrorStatus.PASSWORD_VALIDATION_FAILED);
        }
    }

    // 이메일 형식 검증
    public void isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if(!email.matches(emailRegex))
            throw new GeneralException(ErrorStatus.EMAIL_VALIDATION_FAILED);

    }

    //로그인
    public AuthResponseDTO.LoginResponseDTO login(AuthRequestDTO.LoginRequestDTO loginRequestDTO) {
        Optional<NormalUser> optionalUser = normalUserRepository.findByEmail(loginRequestDTO.getEmail());
        if (optionalUser.isEmpty()) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

        NormalUser user = optionalUser.get();
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus._UNAUTHORIZED);
        }

        String token = tokenProvider.generateToken(user.getName(), user.getId());
        return new AuthResponseDTO.LoginResponseDTO(token, user.getName());
    }

    //토큰 재발행
    public String refreshToken(String token) {
        // 토큰에서 클레임 추출
        Claims claims = tokenProvider.extractClaims(token);

        // 클레임에서 사용자 정보 추출
        String username = claims.getSubject();

        // 사용자 확인
        NormalUser user = normalUserRepository.findByName(username)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        // 새 토큰 발급
        String newToken = tokenProvider.generateToken(user.getName(), user.getId());
        return newToken;
    }


}
