package com.rentsite.rentcarweb.res;


import java.time.LocalDate;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

import com.rentsite.rentcarweb.member.MemberForm;
import com.rentsite.rentcarweb.car.CarForm;

@Getter
@Setter
@Entity
public class ResForm
{
    @Id
    @Column(length = 200)
    private String resNumber;

    @Column(nullable = false)
    private LocalDate resDate;

    @Column(nullable = false)
    private LocalDate useBeginDate;

    @Column(nullable = false)
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "resUserId", referencedColumnName = "id")
    private MemberForm member;

    @ManyToOne
    @JoinColumn(name = "resCarNumber", referencedColumnName = "carNumber")
    private CarForm car;
}
