package com.example.promise.domain.mypage.service;

import com.example.promise.domain.challenge.entity.ChallengeGroup;
import com.example.promise.domain.challenge.entity.ChallengeParticipation;
import com.example.promise.domain.challenge.repository.ChallengeGroupRepository;
import com.example.promise.domain.challenge.repository.ChallengeParticipationRepository;
import com.example.promise.domain.guardian.entity.Guardian;
import com.example.promise.domain.guardian.repository.GuardianRepository;
import com.example.promise.domain.mypage.dto.*;
import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.user.entity.BaseUser;
import com.example.promise.domain.user.entity.NormalUser;
import com.example.promise.domain.user.repository.NormalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final ChallengeParticipationRepository participationRepository;
    private final ChallengeGroupRepository groupRepository;
    private final NormalUserRepository userRepository;
    private final GuardianRepository guardianRepository;

    public List<PointHistoryDto> getPointHistory(Long userId) {
        NormalUser user = userRepository.findById(userId).orElseThrow();
        List<ChallengeParticipation> participations = participationRepository.findAllByUser(user);

        return participations.stream()
                .filter(p -> p.getChallengeGroup().isSettled())
                .map(p -> {
                    ChallengeGroup group = p.getChallengeGroup();
                    boolean isSuccess = p.getTakenCount() >= p.getRequiredDoseCount();
                    long reward = isSuccess ?
                            group.getTotalPoint() / participationRepository.countByChallengeGroupAndIsSuccessTrue(group)
                            : 0;

                    return PointHistoryDto.builder()
                            .challengeDate(group.getChallengeDate())
                            .requiredDoseCount(p.getRequiredDoseCount())
                            .takenCount(p.getTakenCount())
                            .isSuccess(isSuccess)
                            .receivedPoint(reward)
                            .totalPointAfterReward(p.getUser().getPoint())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public UserInfoDto getMyInfo(Long userId) {
        NormalUser user = userRepository.findById(userId).orElseThrow();
        Optional<Guardian> guardian = guardianRepository.findByUser(user);

        return new UserInfoDto(
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getPoint(),
                guardian.map(Guardian::getPhone).orElse(null)
        );
    }

    @Transactional
    public void updateMyInfo(Long userId, UserUpdateRequestDto request) {
        BaseUser user = userRepository.findById(userId).orElseThrow();

        user.setName(request.getName());
        user.setPhone(request.getPhone());

        // 일반 사용자일 경우 수면 시간 업데이트
        if (user instanceof NormalUser normalUser) {
            normalUser.setSleepStartTime(request.getSleepStartTime());
            normalUser.setSleepEndTime(request.getSleepEndTime());
        }

        // 약사일 경우 약국 이름 수정
        if (user instanceof Pharmacist pharmacist && request.getPharmacyName() != null) {
            Pharmacy pharmacy = pharmacist.getPharmacy();
            if (pharmacy != null) {
                pharmacy.setName(request.getPharmacyName());
            }
        }
    }


    @Transactional
    public void registerGuardian(Long userId, GuardianRequestDto request) {
        NormalUser user = userRepository.findById(userId).orElseThrow();
        Guardian guardian = Guardian.builder()
                .user(user)
                .name(request.getName())
                .relation(request.getRelation())
                .phone(request.getPhone())
                .isConnected(true)
                .build();
        guardianRepository.save(guardian);
    }

    @Transactional
    public void updateGuardian(Long userId, GuardianRequestDto request) {
        NormalUser user = userRepository.findById(userId).orElseThrow();
        Guardian guardian = guardianRepository.findByUser(user).orElseThrow();

        guardian.setName(request.getName());
        guardian.setRelation(request.getRelation());
        guardian.setPhone(request.getPhone());
    }
}
