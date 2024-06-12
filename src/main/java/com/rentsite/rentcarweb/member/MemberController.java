package com.rentsite.rentcarweb.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberController
{
    @Autowired
    private MemberService memberService;

    // 회원 메뉴
    @GetMapping("/member")
    public String memberMenu()
    {
        return "memberMenu";
    }

    // 회원등록
    @GetMapping("/member/register")
    public String regMember(Model model)
    {
        return "memberRegister";
    }

    @PostMapping("/member/register")
    public String register(Model model, @RequestParam(value="id") String id, @RequestParam(value="password") String password, @RequestParam
            (value="name")String name, @RequestParam(value="address") String address, @RequestParam(value="phoneNum") String phoneNum)
    {
        // 아이디 중복 여부 확인
        if (memberService.isIdDuplicate(id))
        {
            model.addAttribute("errorMessage", "아이디가 이미 사용 중입니다. 다른 아이디를 입력해주세요.");
            return "memberRegister"; // 아이디 중복 시 다시 회원등록 페이지로 이동
        }
        else
        {
            memberService.registerMember(id, password, name, address, phoneNum);
            model.addAttribute("successMessage", "회원 등록이 되었습니다.");
            return "memberRegister"; // 회원등록 후 메인 페이지로 리다이렉트
        }
    }

    // 회원조회
    @GetMapping("/member/search")
    public String search()
    {
        return "memberSearch"; // 회원조회 페이지
    }

    @PostMapping("/member/search")
    public String searchMember(@RequestParam(value="id", required=false) String id, Model model)
    {
        try
        {
            if (id == null || id.isEmpty())
            {
                // 아이디가 입력되지 않은 경우 전체 회원 정보 조회
                List<MemberForm> members = memberService.getAllMembers();
                model.addAttribute("members", members);
            }
            else
            {
                // 아이디가 입력된 경우 해당 아이디의 회원 정보 조회
                MemberForm member = memberService.findMemberById(id);
                if (member != null)
                {
                    model.addAttribute("member", member);
                }
                else
                {
                    model.addAttribute("errorMessage", "회원 정보를 찾을 수 없습니다.");
                }
            }
        }
        catch (Exception e)
        {
            model.addAttribute("errorMessage", "오류가 발생했습니다. 관리자에게 문의하세요.");
        }
        return "memberSearch";
    }

    // 회원수정
    @GetMapping("/member/modify")
    public String modify()
    {
        return "memberModify";
    }

    @PostMapping("/member/modify")
    public String modiMember(@RequestParam(value = "id") String id, Model model)
    {
        MemberForm member = memberService.findMemberById(id);
        if (member != null)
        {
            model.addAttribute("member", member);
        }
        else
        {
            model.addAttribute("errorMessage", "아이디가 존재하지 않습니다.");
        }
        return "memberModify";
    }

    @PostMapping("/member/update")
    public String updateMember(@RequestParam("id") String id, @RequestParam("password") String password,
                               @RequestParam("name") String name, @RequestParam("address") String address,
                               @RequestParam("phoneNum") String phoneNum, Model model)
    {
        try
        {
            memberService.updateMember(id, password, name, address, phoneNum);
            model.addAttribute("successMessage", "회원 정보가 성공적으로 수정되었습니다.");
        }
        catch (Exception e)
        {
            model.addAttribute("errorMessage", "회원 정보 수정 중 오류가 발생했습니다.");
        }
        return "memberModify";
    }

    // 회원삭제
    @GetMapping("/member/delete")
    public String delete()
    {
        return "memberDelete";
    }

    @PostMapping("/member/delete")
    public String deleteMember(@RequestParam("id") String id, Model model)
    {
        try
        {
            if (memberService.isIdDuplicate(id))
            {
                memberService.deleteMemberById(id);
                model.addAttribute("successMessage", "회원이 성공적으로 삭제되었습니다.");
            }
            else
            {
                model.addAttribute("errorMessage", "아이디가 존재하지 않습니다.");
            }
        }
        catch (Exception e)
        {
            model.addAttribute("errorMessage", "회원 삭제 중 오류가 발생했습니다.");
        }
        return "memberDelete";
    }
}
