package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("events", EventData.getAll());
        return "events/index";
    }

    // lives at /events/create
    @GetMapping("create")
    public String renderCreateEventForm(Model model) {
        model.addAttribute(new Event());
        model.addAttribute("types", EventType.values());
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
        EventData.add(newEvent);
        model.addAttribute("types", EventType.values());
        return "redirect:";

    }

    @GetMapping("delete")
    public String displayDeleteEvent(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", EventData.getAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String deleteEvent(@RequestParam(required = false) int[] eventIds) {
        if(eventIds != null) {
            for (int id : eventIds) {
                EventData.remove(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("edit/{eventId}")
    public String displayEditEvent(Model model, @PathVariable int eventId) {
        model.addAttribute("title","Edit Event " + EventData.getById(eventId).getName());
        model.addAttribute("event", EventData.getById(eventId));
        return "events/edit";
    }

    @PostMapping("edit") //${eventId}
    public String editEvent(int eventId, String name, String description) {
        EventData.getById(eventId).setName(name);
        EventData.getById(eventId).setDescription(description);
        return "redirect:";
    }
}
