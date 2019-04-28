package net.nsnsns.ciscms.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Relational mappings
     **/

    @ManyToOne
    @ToString.Exclude
    private Student student;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("dueDate asc")
    @ToString.Exclude
    private List<Gradeable> gradeables = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    private Instructor instructor;

    @ManyToOne
    @ToString.Exclude
    private Semester semester;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private CourseWeekSchedule weekschedule = new CourseWeekSchedule();

    /**
     * Fields
     **/

    private String title;
    private String code;
    private Integer crn;
    private String building;
    private String room;
    private LocalTime startTime;
    private LocalTime endTime;

    private boolean isCurrent;
    private Float calculatedGrade;

    public float getCurrentGrade() {
        return gradeables.stream()
                .map(gradeable -> gradeable.getGrade() / 100 * gradeable.getWeight() / 100)
                .reduce(Float::sum)
                .orElse(0f);
    }
}
