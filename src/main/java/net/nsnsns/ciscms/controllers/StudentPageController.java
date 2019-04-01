package net.nsnsns.ciscms.controllers;

import net.nsnsns.ciscms.models.Student;
import net.nsnsns.ciscms.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class StudentPageController {

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
        return "studentpage";
    }

    @PostMapping
    public String addCourse(@RequestParam(name = "addCourse") String addCourse) {
        System.out.println("Adding course");

        return "studentpage";
    }

}
