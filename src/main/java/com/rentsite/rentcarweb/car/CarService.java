package com.rentsite.rentcarweb.car;

import java.util.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CarService
{
    private final CarRepository carRepository;

    // 차량등록
    public void registerCar(String carNumber, String carName, String carColor, int carSize, String carMaker)
    {
        CarForm car = new CarForm();
        car.setCarNumber(carNumber);
        car.setCarName(carName);
        car.setCarColor(carColor);
        car.setCarSize(carSize);
        car.setCarMaker(carMaker);
        carRepository.save(car);
    }

    // 차량번호가 데이터베이스에 존재하는지 확인해주는 메소드
    public boolean isCarNumberDuplicate(String carNumber) {
        return carRepository.existsByCarNumber(carNumber);
    }

    // 차량번호가 데이터베이스에 있는지 확인하고 있으면 리턴 없으면 널을 리턴
    public CarForm findCarByCarNumber(String carNumber) {
        Optional<CarForm> car = carRepository.findByCarNumber(carNumber);
        return car.orElse(null);
    }

    // 모든 차량 조회
    public List<CarForm> getAllCars() {
        return carRepository.findAll();
    }

    // 차량정보 수정하기
    public void updateCar(String carNumber, String carName, String carColor, int carSize, String carMaker) {
        CarForm car = findCarByCarNumber(carNumber);
        if (car != null) {
            car.setCarName(carName);
            car.setCarColor(carColor);
            car.setCarSize(carSize);
            car.setCarMaker(carMaker);
            carRepository.save(car);
        } else {
            throw new RuntimeException("존재하지 않는 차량 정보입니다.");
        }
    }

    // 차량정보 삭제하기
    public void deleteCarByCarNumber(String carNumber) {
        carRepository.deleteById(carNumber);
    }
}
