package net.nsnsns.ciscms.services;

import net.nsnsns.ciscms.repos.CourseSyllabusRepository;
import net.nsnsns.ciscms.repos.DocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class SyllabusService {
    private final DocumentRepository documentRepository;
    private final CourseSyllabusRepository syllabusRepository;

    public SyllabusService(DocumentRepository documentRepository, CourseSyllabusRepository syllabusRepository) {
        this.documentRepository = documentRepository;
        this.syllabusRepository = syllabusRepository;
    }

    public Integer getSyllabusCount(Integer courseId) {

        return syllabusRepository.countByCourse_Id(courseId);
    }


}
