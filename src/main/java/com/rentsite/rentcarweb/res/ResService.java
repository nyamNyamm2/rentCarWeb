package com.rentsite.rentcarweb.res;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import lombok.RequiredArgsConstructor;

import com.rentsite.rentcarweb.member.MemberRepository;
import com.rentsite.rentcarweb.car.*;
import com.rentsite.rentcarweb.member.*;

import org.springframework.dao.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class ResService
{
    private final CarRepository carRepository;
    private final MemberRepository memberRepository;
    private final ResRepository resRepository;


    public boolean isCarNumberDuplicate(String carNumber) {
        return carRepository.findByCarNumber(carNumber).isPresent();
    }

    public boolean isMemberIdDuplicate(String memberId) {
        return memberRepository.findById(memberId).isPresent();
    }

    // 예약 등록
    public void createRes(String resNumber, LocalDate resDate, LocalDate useBeginDate, LocalDate returnDate, String carNumber, String memberId) {
        CarForm car = carRepository.findByCarNumber(carNumber).orElseThrow(() -> new IllegalArgumentException("Invalid car number"));
        MemberForm member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        ResForm res = new ResForm();
        res.setResNumber(resNumber);
        res.setResDate(resDate);
        res.setUseBeginDate(useBeginDate);
        res.setReturnDate(returnDate);
        res.setCar(car);
        res.setMember(member);
        resRepository.save(res);
    }
    public boolean isResNumberExist(String resNumber) {
        Optional<ResForm> existingRes = resRepository.findByResNumber(resNumber);
        return existingRes.isPresent();
    }

    // 모든 예약 조회
    public List<ResForm> getAllRess() {
        return resRepository.findAll();
    }

    // 예약번호가 데이터베이스에 있는지 확인하고 있으면 리턴 없으면 널을 리턴
    public ResForm findResByResNumber(String resNumber) {
        Optional<ResForm> res = resRepository.findByResNumber(resNumber);
        return res.orElse(null);
    }

    // 예약 수정
    public void editRes(String resNumber, String carNumber, LocalDate resDate, LocalDate useBeginDate, LocalDate returnDate) {
        ResForm res = findResByResNumber(resNumber);
        if (res != null) {
            res.getCar().setCarNumber(carNumber);
            res.setResDate(resDate);
            res.setUseBeginDate(useBeginDate);
            res.setReturnDate(returnDate);
            resRepository.save(res);
        } else {
            throw new IllegalArgumentException("예약 수정에 실패하셨습니다.");
        }
    }

    // 예약정보 삭제하기
    public void deleteResByResNumber(String resNumber) {
        resRepository.deleteById(resNumber);
    }
}
