package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String displayEvents(@RequestParam (required = false) Integer categoryId, Model model) {
        if(categoryId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepo.findAll());
        } else {
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if(result.isPresent()) {
                EventCategory category = result.get();
                model.addAttribute("title", "Events in category: " + category.getName());
                model.addAttribute("events", category.getEvents());
            } else {
                model.addAttribute("title", "Invalid Category Id: " + categoryId);
            }
        }
            return "events/index";
    }

    // lives at /events/create
    @GetMapping("create")
    public String renderCreateEventForm(Model model) {
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "events/create";
    }

    // lives at /events/create
//    @PostMapping("create")
//    public String createEvent(@RequestParam String eventName,
//                              @RequestParam String eventDescription) {
//        EventData.add(new Event(eventName, eventDescription));
//        return "redirect:";
//    }
    @PostMapping("create")
    public String createEvent(@ModelAttribute @Valid Event newEvent, Errors errors, Model model) {
        if(errors.hasErrors()) {
            return "events/create";
        }
        eventRepo.save(newEvent);
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "redirect:";

    }

    @GetMapping("delete")
    public String displayDeleteEvent(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepo.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String deleteEvent(@RequestParam(required = false) int[] eventIds) {
        if(eventIds != null) {
            for (int id : eventIds) {
                eventRepo.deleteById(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("details/{eventId}")
    public String displayEventDetails(Model model, @PathVariable int eventId) {
        model.addAttribute("title", "Event Details");
        model.addAttribute("event", eventRepo.findById(eventId));
        return "events/details";
    }

    @GetMapping("edit/{eventId}")
    public String displayEditEvent(Model model, @PathVariable int eventId) {
        model.addAttribute("title","Edit Event " + eventRepo.returnName(eventId));
        model.addAttribute("event", eventRepo.findById(eventId));
        return "events/edit";
    }

//    @PostMapping("edit") //${eventId}
//    public String editEvent(int eventId, String name, String description) {
//        EventRepo.findById(eventId).save(name);
//        EventRepo.findById(eventId).setDescription(description);
//        return "redirect:";
//    }
}
