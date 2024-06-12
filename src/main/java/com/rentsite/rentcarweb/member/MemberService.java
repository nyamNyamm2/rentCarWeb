package com.rentsite.rentcarweb.member;

import java.util.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService
{
    private final MemberRepository memberRepository;

    // 회원등록
    public void registerMember(String id, String password, String name, String address, String phoneNum)
    {
        MemberForm member = new MemberForm();
        member.setId(id);
        member.setPassword(password);
        member.setName(name);
        member.setAddress(address);
        member.setPhoneNum(phoneNum);
        memberRepository.save(member);
    }

    // 아이디가 데이터베이스에 존재하는지 확인해주는 메소드
    public boolean isIdDuplicate(String id)
    {
        return memberRepository.existsById(id);
    }

    // 아이디가 데이터베이스에 있는지 확인하고 있으면 리턴 없으면 널을 리턴
    public MemberForm findMemberById(String id)
    {
        Optional<MemberForm> member = memberRepository.findById(id);
        return member.orElse(null);
    }

    // 모든 회원 조회
    public List<MemberForm> getAllMembers()
    {
        return memberRepository.findAll();
    }

    // 회원정보 수정하기
    public void updateMember(String id, String password, String name, String address, String phoneNum)
    {
        MemberForm member = findMemberById(id);
        if (member != null)
        {
            member.setPassword(password);
            member.setName(name);
            member.setAddress(address);
            member.setPhoneNum(phoneNum);
            memberRepository.save(member);
        }
        else
        {
            throw new RuntimeException("Member not found");
        }
    }

    // 회원정보 삭제
    public void deleteMemberById(String id)
    {
        memberRepository.deleteById(id);
    }

}
