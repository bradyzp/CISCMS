package net.nsnsns.ciscms.services;

import net.nsnsns.ciscms.models.Gradeable;
import net.nsnsns.ciscms.repos.GradeableRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class GradeableService {
    private final GradeableRepository gradeableRepository;

    GradeableService(GradeableRepository gradeableRepository) {
        this.gradeableRepository = gradeableRepository;
    }

    @PreAuthorize("#gradeable.owner.username == authentication.name")
    public void addGradeable(Gradeable gradeable) {
        this.gradeableRepository.save(gradeable);
    }

}
