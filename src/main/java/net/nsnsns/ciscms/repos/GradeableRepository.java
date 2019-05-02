package net.nsnsns.ciscms.repos;

import net.nsnsns.ciscms.models.Gradeable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeableRepository extends JpaRepository<Gradeable, Integer> {
}
