package net.nsnsns.ciscms.repos;

import net.nsnsns.ciscms.models.CourseSyllabus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseSyllabusRepository extends JpaRepository<CourseSyllabus, Integer> {
    Optional<CourseSyllabus> getById(Integer id);

    Integer countByCourse_Id(Integer courseId);

    List<CourseSyllabus> findAllByCourse_Id(Integer courseId);
}
