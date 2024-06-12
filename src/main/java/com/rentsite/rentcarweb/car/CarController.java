package com.rentsite.rentcarweb.car;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CarController 
{
    @Autowired
    private CarService carService;

    @GetMapping("/car")
    public String carMenu()
    {
        return "carMenu";
    }

    // 차량 등록
    @GetMapping("/car/register")
    public String regCar(Model model)
    {
        return "carRegister";
    }

    @PostMapping("/car/register")
    public String registerCar(Model model, @RequestParam(value="carNumber") String carNumber, @RequestParam(value="carName") String carName, @RequestParam
            (value="carColor")String carColor, @RequestParam(value="carSize") int carSize, @RequestParam(value="carMaker") String carMaker)
    {
        // 차량 중복 여부 확인
        if (carService.isCarNumberDuplicate(carNumber))
        {
            model.addAttribute("error", "이미 등록되어 있는 차량번호입니다");
            return "carRegister"; // 차량번호 중복 시 다시 차량등록 페이지로 이동
        }
        else
        {
            carService.registerCar(carNumber, carName, carColor, carSize, carMaker);
            model.addAttribute("success", "새로운 차량이 등록되었습니다");
            return "carRegister"; // 차량 등록 성공 메시지 출력
        }
    }

    // 차량 조회
    @GetMapping("/car/search")
    public String searchCar()
    {
        return "carSearch";
    }

    @PostMapping("/car/search")
    public String searchCar(@RequestParam(value="carNumber", required=false) String carNumber, Model model)
    {
        try
        {
            if (carNumber == null || carNumber.isEmpty())
            {
                // 차량번호가 입력되지 않은 경우 전체 차량 정보 조회
                List<CarForm> cars = carService.getAllCars();
                model.addAttribute("cars", cars);
            }
            else
            {
                // 차량번호가 입력된 경우 해당 차량의 차량 정보 조회
                CarForm car = carService.findCarByCarNumber(carNumber);
                if (car != null)
                {
                    model.addAttribute("car", car);
                }
                else
                {
                    model.addAttribute("error", "잘못된 차량 번호입니다");
                }
            }
        }
        catch (Exception e)
        {
            model.addAttribute("error", "오류가 발생했습니다");
        }
        return "carSearch";
    }

    // 차량 수정
    @GetMapping("/car/modify")
    public String modiCar()
    {
        return "carModify";
    }

    @PostMapping("/car/modify")
    public String modiCar(@RequestParam(value = "carNumber") String carNumber, Model model)
    {
        CarForm car = carService.findCarByCarNumber(carNumber);
        if (car != null)
        {
            model.addAttribute("car", car);
        }
        else
        {
            model.addAttribute("error", "잘못된 차량번호 입니다");
        }
        return "carModify";
    }

    @PostMapping("/car/update")
    public String updateCar(@RequestParam("carNumber") String carNumber, @RequestParam("carName") String carName,
                            @RequestParam("carColor") String carColor, @RequestParam("carSize") int carSize,
                            @RequestParam("carMaker") String carMaker, Model model)
    {
        try
        {
            carService.updateCar(carNumber, carName, carColor, carSize, carMaker);
            model.addAttribute("success", "성공적으로 차량 정보를 수정했습니다");
        }
        catch (Exception e)
        {
            model.addAttribute("error", "차량 정보 수정 중 오류가 발생했습니다");
        }
        return "carModify";
    }

    // 차량삭제
    @GetMapping("/car/delete")
    public String deleteCar()
    {
        return "carDelete";
    }

    @PostMapping("/car/delete")
    public String deleteCar(@RequestParam(value = "carNumber") String carNumber, Model model)
    {
        try
        {
            if (carService.isCarNumberDuplicate(carNumber))
            {
                carService.deleteCarByCarNumber(carNumber);
                model.addAttribute("success", "차량 삭제가 완료되었습니다");
            }
            else
            {
                model.addAttribute("error", "잘못된 차량 번호입니다");
            }
        }
        catch (Exception e)
        {
            model.addAttribute("error", "차량 삭제 중 오류가 발생했습니다");
        }
        return "carDelete";
    }
}