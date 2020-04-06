package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.models.EventCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("eventCategories")
public class EventCategoryController {

    @Autowired
    private EventCategoryRepository eventCategoryRepo;

    @GetMapping
    public String displayAllEventCategories (Model model) {
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", eventCategoryRepo.findAll());
        return "eventCategories/index";
    }

    @GetMapping("/create")
    public String renderCreateEventCategoryForm (Model model) {
        model.addAttribute("title", "Create Category");
        model.addAttribute(new EventCategory());
        return "eventCategories/create";
    }

    @PostMapping("/create")
    public String processCreateEventCategoryForm (@ModelAttribute @Valid EventCategory eventCat, Errors errors, Model model) {
        if(errors.hasErrors()) {
            return "eventCategories/create";
        }
        eventCategoryRepo.save(eventCat);
        model.addAttribute("title", "Create Category");
        return "redirect:";
    }
}
