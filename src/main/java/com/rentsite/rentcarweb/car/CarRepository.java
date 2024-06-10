package com.rentsite.rentcarweb.car;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarForm, String>
{
    boolean existsByCarNumber(String carNumber);
    Optional<CarForm> findByCarNumber(String carNumber);
}
