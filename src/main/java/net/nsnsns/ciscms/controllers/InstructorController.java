package net.nsnsns.ciscms.controllers;

import net.nsnsns.ciscms.models.Instructor;
import net.nsnsns.ciscms.services.InstructorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/instructor")
public class InstructorController {
    private static final String VIEW = "instructor_detail";

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping(path = "/{id}")
    public String viewInstructor(@PathVariable("id") Integer id, Model model) {
        final Instructor instructor = instructorService.getInstructor(id).orElseThrow();

        model.addAttribute(instructor);

        return VIEW;
    }

}
