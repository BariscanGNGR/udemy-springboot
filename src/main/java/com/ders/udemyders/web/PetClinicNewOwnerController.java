package com.ders.udemyders.web;

import com.ders.udemyders.model.Owner;
import com.ders.udemyders.service.PetClinicService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class PetClinicNewOwnerController {

    private PetClinicService petClinicService;

    @RequestMapping(value = "/owners/new", method = RequestMethod.GET)
    private String newOwner(){
        return "newOwner";
    }

    @ModelAttribute
    public Owner initModel(){
        return new Owner();
    }

    @RequestMapping(value = "/owners/new", method = RequestMethod.POST)
    public String handleFormSubmit(@ModelAttribute @Valid Owner owner, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "newOwner";
        }
        petClinicService.createOwner(owner);
        redirectAttributes.addFlashAttribute("message", "Owner created with id : "+owner.getId());
        return "redirect:/owners";
    }
}
