package com.rentsite.rentcarweb.res;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResRepository extends JpaRepository<ResForm, String>
{
    Optional<ResForm> findByResNumber(String resNumber);
    void deleteByresNumber(String resNumber);
}

