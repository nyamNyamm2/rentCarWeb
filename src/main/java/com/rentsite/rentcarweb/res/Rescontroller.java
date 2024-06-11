package com.rentsite.rentcarweb.res;

import com.rentsite.rentcarweb.car.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Rescontroller
{
    @Autowired
    private ResService resService;
    @GetMapping("/res")
    public String resMenu() {
        return "resMenu";
    }

    // 예약등록
    @GetMapping("/res/register")
    public String resForm(Model model) {
        return "resRegister";
    }


    // 차량 번호와 회원 아이디 확인
    @PostMapping("/res/check")
    public String checkCarAndMember(@RequestParam("carNumber") String carNumber, @RequestParam("memberId") String memberId, Model model) {
        if (resService.isCarNumberDuplicate(carNumber) && resService.isMemberIdDuplicate(memberId)) {
            model.addAttribute("carNumber", carNumber);
            model.addAttribute("memberId", memberId);
            return "resRegister2";
        } else {
            model.addAttribute("errorMessage", "차량 번호 또는 회원 아이디가 존재하지 않습니다.");
            return "resRegister";
        }
    }

    // 예약 등록 두 번째 단계
    @PostMapping("/res/register2")
    public String registerRes(@RequestParam("resNumber") String resNumber, @RequestParam("resDate") LocalDate resDate,
                              @RequestParam("useBeginDate") LocalDate useBeginDate,
                              @RequestParam("returnDate") LocalDate returnDate,
                              @RequestParam("carNumber") String carNumber,
                              @RequestParam("memberId") String memberId, Model model) {
        try {
            if (resService.isResNumberExist(resNumber)) {
                model.addAttribute("errorMessage", "이미 존재하는 예약번호입니다.");
                return "resRegister2";
            }
            // 예약 등록
            resService.createRes(resNumber, resDate, useBeginDate, returnDate, carNumber, memberId);
            // 예약 성공 메시지를 모델에 추가
            model.addAttribute("successMessage", "예약이 성공적으로 등록되었습니다.");
            // 성공 페이지로 이동
            return "resRegister2";
        } catch (Exception e) {
            // 오류 발생 시 오류 메시지를 모델에 추가
            model.addAttribute("errorMessage", "예약 등록 중 오류가 발생했습니다.");
            // 예약 등록 페이지로 다시 이동
            return "resRegister2";
        }
    }

    // 예약D-Day
    @GetMapping("/res/register0")
    public String resForm2(Model model) {
        return "resRegister0";
    }

    // 차량 번호와 회원 아이디 확인
    @PostMapping("/res/check0")
    public String checkCarAndMember2(@RequestParam("carNumber") String carNumber, @RequestParam("memberId") String memberId, Model model) {
        if (resService.isCarNumberDuplicate(carNumber) && resService.isMemberIdDuplicate(memberId)) {
            model.addAttribute("carNumber", carNumber);
            model.addAttribute("memberId", memberId);
            return "resDDay";
        } else {
            model.addAttribute("errorMessage", "차량 번호 또는 회원 아이디가 존재하지 않습니다.");
            return "resRegister0";
        }
    }
    // 예약 D-Day 두 번째 단계
    @PostMapping("/res/regDDay")
    public String registerRes2(@RequestParam("resNumber") String resNumber,
                              @RequestParam("returnMonth") int returnMonth,
                              @RequestParam("carNumber") String carNumber,
                              @RequestParam("memberId") String memberId, Model model) {
        try {
            if (resService.isResNumberExist(resNumber)) {
                model.addAttribute("errorMessage", "이미 존재하는 예약번호입니다.");
                return "resDDay";
            }
            LocalDate currentDate = LocalDate.now();
            LocalDate returnDate = currentDate.plusDays(returnMonth * 30L);

            // 예약 등록
            resService.createRes(resNumber, currentDate, currentDate, returnDate, carNumber, memberId);
            // 예약 성공 메시지를 모델에 추가
            model.addAttribute("successMessage", "예약이 성공적으로 등록되었습니다.");
            // 성공 페이지로 이동
            return "resDDay";
        } catch (Exception e) {
            // 오류 발생 시 오류 메시지를 모델에 추가
            model.addAttribute("errorMessage", "예약 등록 중 오류가 발생했습니다.");
            // 예약 등록 페이지로 다시 이동
            return "resDDay";
        }
    }

    // 예약조회
    @GetMapping("/res/search")
    public String resview() {
        return "resSearch";
    }

    @PostMapping("/res/search")
    public String resview(@RequestParam(value="resNumber", required=false) String resNumber, Model model) {
        try {
            if (resNumber == null || resNumber.isEmpty()) {
                // 예약번호가 입력되지 않은 경우 전체 예약 정보 조회
                List<ResForm> ress = resService.getAllRess();
                model.addAttribute("ress", ress);
            } else {
                // 차량번호가 입력된 경우 해당 차량의 차량 정보 조회
                ResForm res = resService.findResByResNumber(resNumber);
                if (res != null) {
                    model.addAttribute("res", res);
                } else {
                    model.addAttribute("errorMessage", "예약 정보를 찾을 수 없습니다.");
                }
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "오류가 발생했습니다. 관리자에게 문의하세요.");
        }
        return "resSearch";
    }


    // 예약수정
    @GetMapping("/res/modify")
    public String editRes() {
        return "resModify";
    }

    @PostMapping("/res/modify")
    public String editRes(@RequestParam(value = "resNumber") String resNumber, Model model) {
        ResForm res = resService.findResByResNumber(resNumber);
        if (res != null) {
            model.addAttribute("res", res);
        } else {
            model.addAttribute("errorMessage", "예약번호가 존재하지 않습니다.");
        }
        return "resModify";
    }

    @PostMapping("/res/update")
    public String editRes(@RequestParam(value = "resNumber") String resNumber,
                          @RequestParam(value = "carNumber") String carNumber,
                          @RequestParam(value = "resDate") LocalDate resDate,
                          @RequestParam(value = "useBeginDate") LocalDate useBeginDate,
                          @RequestParam(value = "returnDate") LocalDate returnDate,
                          Model model) {
        try {
            resService.editRes(resNumber, carNumber, resDate, useBeginDate, returnDate);
            model.addAttribute("successMessage", "예약 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "예약 정보 수정 중 오류가 발생했습니다.");
        }
        return "resModify";
    }

    // 예약삭제
    @GetMapping("/res/delete")
    public String deleteRes() {
        return "resDelete";
    }

    @PostMapping("/res/delete")
    public String deleteRes(@RequestParam(value = "resNumber") String resNumber, Model model) {
        try {
            if (resService.isResNumberExist(resNumber)) {
                resService.deleteResByResNumber(resNumber);
                model.addAttribute("successMessage", "예약이 성공적으로 삭제되었습니다.");
            }
            else {
                model.addAttribute("errorMessage", "해당 예약 번호가 존재하지 않습니다.");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "예약 삭제 중 오류가 발생했습니다.");
        }
        return "resDelete";
    }

    @GetMapping("/version")
    public String versionInfo(Model model) {
        model.addAttribute("version", "v1.0");
        model.addAttribute("releaseDate", "24.06.10 Fri");
        model.addAttribute("author", "심기윤");
        return "version";
    }

    // 예약자 아이디 변경 화면으로 이동
    @GetMapping("/res/send")
    public String sendRes() {
        return "resSend";
    }

    @PostMapping("/res/send")
    public String sendRes(@RequestParam(value = "resNumber") String resNumber, Model model) {
        ResForm res = resService.findResByResNumber(resNumber);
        if (res != null) {
            model.addAttribute("res", res);
        } else {
            model.addAttribute("errorMessage", "예약번호가 존재하지 않습니다.");
        }
        return "resSend";
    }

    @PostMapping("/res/send/update")
    public String updateResMember(@RequestParam(value = "resNumber") String resNumber,
                                  @RequestParam(value = "newMemberId") String newMemberId,
                                  Model model) {
        try {
            resService.updateResMember(resNumber, newMemberId);
            model.addAttribute("successMessage", "예약자의 아이디가 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "예약자 아이디 변경 중 오류가 발생했습니다.");
        }
        return "resSend";
    }
}
