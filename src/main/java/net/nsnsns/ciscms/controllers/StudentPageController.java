package net.nsnsns.ciscms.controllers;

import net.nsnsns.ciscms.models.Student;
import net.nsnsns.ciscms.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class StudentPageController {
    private static final String PROFILE = "user_profile";

    private final StudentService studentService;

    @Autowired
    StudentPageController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ModelAttribute(name = "student")
    private Student student() {
        return studentService.getAuthenticatedStudent().orElseThrow();
    }

    @GetMapping
    public String studentPage(Model model) {
        return PROFILE;
    }

}
