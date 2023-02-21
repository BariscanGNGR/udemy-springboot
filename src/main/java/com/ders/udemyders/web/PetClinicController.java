package com.ders.udemyders.web;

import com.ders.udemyders.service.PetClinicService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class PetClinicController {

    private PetClinicService petClinicService;

    @RequestMapping("/owners")
    @ResponseBody
    public ModelAndView getOwners(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("owners",petClinicService.findOwners());
        modelAndView.setViewName("owners");
        return modelAndView;
    }

    @RequestMapping(value = {"/","/index.html"})
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/login.html")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/pcs")
    @ResponseBody
    public String welcome(){
        return "Welcome to PetClinic world!";
    }
}
