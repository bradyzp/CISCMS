package net.nsnsns.ciscms.controllers;

import net.nsnsns.ciscms.models.Course;
import net.nsnsns.ciscms.models.Gradeable;
import net.nsnsns.ciscms.services.CourseService;
import net.nsnsns.ciscms.services.GradeableService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gradeable")
public class GradeableController {
    private final GradeableService gradeableService;
    private final CourseService courseService;

    GradeableController(final GradeableService gradeableService, final CourseService courseService) {
        this.gradeableService = gradeableService;
        this.courseService = courseService;
    }

    @GetMapping("/{cid}")
    public String editGradeables(@PathVariable(name = "cid") Integer courseId, Model model) {
        Course course = courseService.getCourse(courseId).orElseThrow();

        model.addAttribute("course", course);
        System.out.println("Setting course: " + course.toString());
        return "gradeable_editor";
    }

    @PostMapping(params = {"addRow"})
    public String addRow(Course course) {
        Gradeable g = new Gradeable();
        g.setCourse(course);
        course.getGradeables().add(g);

        return "gradeable_editor";
    }

    @PostMapping(params = {"save"})
    public String saveGradeables(Course course) {
        System.out.println("Saving course: " + course.toString());
        courseService.saveCourse(course);

        return "redirect:/course/" + course.getId();
    }


//    @GetMapping("/{id}/create")
//    public String addGradeable(@PathVariable(name = "id") Long id, Model model) {
//        Optional<Course> course = courseService.getCourse(id);
//        if (course.isPresent()) {
//            Gradeable gradeable = new Gradeable();
//            model.addAttribute("action", "create");
//            model.addAttribute("cid", id);
//            model.addAttribute("gradeable", gradeable);
//            return "gradeable_editor";
//
//        } else {
//            System.out.println("Invalid course id");
//            return "redirect:/error";
//        }
//    }
//
//    @GetMapping("/{cid}/edit/{gid}")
//    public String editGradeable(@PathVariable(name = "cid") Long courseId,
//                                @PathVariable(name = "gid") Long gradeableId,
//                                Model model) {
//        Gradeable gradeable = gradeableService.getGradeable(gradeableId);
//
//        System.out.println("Editing gradeable: " + gradeable);
//
//        model.addAttribute("cid", courseId);
//        model.addAttribute("gradeable", gradeable);
//        return "gradeable_editor";
//    }
//
//    @PostMapping("/{cid}/upsert")
//    public String upsertGradeable(@PathVariable(name = "cid") Long courseId, @Valid Gradeable gradeable) {
//        System.out.println("Updating gradeable:");
//        Course course = courseService.getCourse(courseId).orElseThrow();
//        gradeable.setCourse(course);
//        System.out.println(gradeable);
//        gradeableService.addGradeable(gradeable);
//
//        return "redirect:/course/" + courseId;
//    }
}
