package net.nsnsns.ciscms.services;

import net.nsnsns.ciscms.models.CourseSyllabus;
import net.nsnsns.ciscms.repos.CourseSyllabusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyllabusService {
    private final CourseSyllabusRepository syllabusRepository;

    public SyllabusService(CourseSyllabusRepository syllabusRepository) {
        this.syllabusRepository = syllabusRepository;
    }

    public Integer getSyllabusCount(Integer courseId) {

        return syllabusRepository.countByCourse_Id(courseId);
    }

    public List<CourseSyllabus> getSyllabusForCourse(Integer courseId) {
        return syllabusRepository.findAllByCourse_Id(courseId);
    }


}
