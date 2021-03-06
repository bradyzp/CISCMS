package net.nsnsns.ciscms.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Student owner;

    private String title;
    private String filename;
    private String contentType;
    private String contentUrl;

    private byte[] content;

    @CreationTimestamp
    private Timestamp created;

}
