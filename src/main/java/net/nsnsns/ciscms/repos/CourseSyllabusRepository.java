package net.nsnsns.ciscms.repos;

import net.nsnsns.ciscms.models.CourseSyllabus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseSyllabusRepository extends JpaRepository<CourseSyllabus, Integer> {
    public Optional<CourseSyllabus> getById(Integer id);

    public Integer countByCourse_Id(Integer courseId);
}
