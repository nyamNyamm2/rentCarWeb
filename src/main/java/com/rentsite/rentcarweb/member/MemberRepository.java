package com.rentsite.rentcarweb.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberForm, String> {

}
