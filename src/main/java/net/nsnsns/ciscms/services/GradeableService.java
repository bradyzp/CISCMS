package net.nsnsns.ciscms.services;

import net.nsnsns.ciscms.exceptions.ResourceNotFoundException;
import net.nsnsns.ciscms.models.Gradeable;
import net.nsnsns.ciscms.repos.GradeableRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    @Transactional
    @PostAuthorize("returnObject.owner.username == authentication.name")
    public Gradeable getGradeable(Long id) {
        return gradeableRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Gradeable with id: " + id + "not found"));
    }

}
