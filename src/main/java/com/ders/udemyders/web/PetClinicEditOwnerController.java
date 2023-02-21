package com.ders.udemyders.web;

import com.ders.udemyders.model.Owner;
import com.ders.udemyders.service.PetClinicService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class PetClinicEditOwnerController {
    private PetClinicService petClinicService;

    @RequestMapping(value = "/owners/update/{id}",method = RequestMethod.GET)
    public String loadOwner(@PathVariable Long id, ModelMap modelMap){
        Owner owner = petClinicService.findOwner(id);
        modelMap.put("owner", owner);
        return "editOwner";
    }

    @RequestMapping(value = "/owners/update/{id}" ,method = RequestMethod.POST)
    public String handleFormSubmit(@ModelAttribute @Valid Owner owner, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "editOwner";
        }
        petClinicService.updateOwner(owner);
        redirectAttributes.addFlashAttribute("message","Owner edited with id : "+ owner.getId());
        return "redirect:/owners";
    }
}
