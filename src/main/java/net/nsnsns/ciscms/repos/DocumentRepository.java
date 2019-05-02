package net.nsnsns.ciscms.repos;

import net.nsnsns.ciscms.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Optional<Document> getByTitle(String title);
}
