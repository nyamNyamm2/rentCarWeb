package com.rentsite.rentcarweb.car;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

import com.rentsite.rentcarweb.res.ResForm;

@Getter
@Setter
@Entity
public class CarForm
{
    @Id
    @Column(length = 200)
    private String carNumber;

    @Column(length = 200)
    private String carName;

    @Column(length = 200)
    private String carColor;

    @Column
    private Integer carSize;

    @Column(length = 200)
    private String carMaker;

    @OneToMany(mappedBy = "car")
    private List<ResForm> cars;
}
