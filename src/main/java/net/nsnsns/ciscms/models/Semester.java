package net.nsnsns.ciscms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Semester {
    @OneToMany(mappedBy = "semester")
    List<Course> courses;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @ToString.Exclude
    private Student owner;
    private Integer year;
    @Enumerated(value = EnumType.ORDINAL)
    private Season season;

    @Override
    public String toString() {
        return String.format("%s - %d", season, year);
    }

    private Date startDate;
    private Date endDate;

    public Season[] getSeasons() {
        return Season.values();
    }

    public enum Season {
        SPRING,
        FALL,
        SUMMER,
        MAYMESTER,
        WINTER
    }
}
