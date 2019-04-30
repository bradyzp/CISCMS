package net.nsnsns.ciscms.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class CourseWeekSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder();
        if (monday)
            repr.append("M/");
        if (tuesday)
            repr.append("T/");
        if (wednesday)
            repr.append("W/");
        if (thursday)
            repr.append("Th/");
        if (friday)
            repr.append("F/");
        if (saturday)
            repr.append("S/");
        if (sunday)
            repr.append("Su/");
        repr.deleteCharAt(repr.length() - 1);

        return repr.toString();
    }
}
