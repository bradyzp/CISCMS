package net.nsnsns.ciscms.controllers;

import net.nsnsns.ciscms.models.Course;
import net.nsnsns.ciscms.models.CourseSyllabus;
import net.nsnsns.ciscms.models.Document;
import net.nsnsns.ciscms.repos.CourseSyllabusRepository;
import net.nsnsns.ciscms.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/course/docs")
public class DocumentController {
    private final CourseService courseService;
    private final StudentService studentService;
    private final SyllabusService syllabusService;
    private final CourseSyllabusRepository syllabusRepository;

    DocumentController(CourseService courseService, StudentService studentService, SyllabusService syllabusService,
                       CourseSyllabusRepository syllabusRepository) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.syllabusService = syllabusService;
        this.syllabusRepository = syllabusRepository;
    }

    @GetMapping("/syllabus/{id}")
    public void viewSyllabus(@PathVariable("id") Integer id, HttpServletResponse response) {
        CourseSyllabus syllabus = syllabusRepository.getById(id).orElseThrow();
        final Document document = syllabus.getDocument();
        Resource resource = new ByteArrayResource(document.getContent());

        response.setContentType(document.getContentType());
        response.addHeader("Content-Disposition", "attachment; filename=" + document.getFilename());
        try {
            resource.getInputStream().transferTo(response.getOutputStream());
        } catch (IOException e) {
            System.out.println("Unable to return document");
        }

    }

    @PostMapping(value = "/upload", params = {"syl"})
    public String uploadSyllabus(@RequestParam("file") MultipartFile file,
                                 @RequestParam("comment") String comment,
                                 @RequestParam("cid") Integer courseId) throws IOException {
        final Course course = courseService.getCourse(courseId).orElseThrow();

        CourseSyllabus syllabus = new CourseSyllabus();

        Document document = new Document();
        document.setOwner(studentService.getAuthenticatedStudent().orElseThrow());
        document.setTitle(file.getOriginalFilename());
        document.setFilename(file.getOriginalFilename());
        document.setContentType(file.getContentType());
        document.setContentUrl("empty");
        document.setContent(file.getBytes());

        syllabus.setComment(comment);
        syllabus.setDocument(document);
        syllabus.setCourse(course);
        syllabus.setVersion(syllabusService.getSyllabusCount(course.getId()) + 1);

        syllabusRepository.save(syllabus);

        return "redirect:/course/" + courseId;
    }
}
