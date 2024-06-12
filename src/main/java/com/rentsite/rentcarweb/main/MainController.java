package com.rentsite.rentcarweb.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController
{
    @GetMapping("/main")
    public String main()
    {
        return "main";
    }

    @GetMapping("/")
    public String redirectToMain()
    {
        return "redirect:/main";
    }
}
