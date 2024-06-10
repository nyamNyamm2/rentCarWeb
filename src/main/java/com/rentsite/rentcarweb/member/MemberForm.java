package com.rentsite.rentcarweb.member;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

import com.rentsite.rentcarweb.res.ResForm;

@Getter
@Setter
@Entity
public class MemberForm
{
    @Id
    @Column(length = 200)
    private String id;

    @Column(length = 200)
    private String password;

    @Column(length = 200)
    private String name;

    @Column(length = 200)
    private String address;

    @Column(length = 200)
    private String phoneNum;

    @OneToMany(mappedBy = "member")
    private List<ResForm> reservations;
}
