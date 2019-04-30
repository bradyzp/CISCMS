package net.nsnsns.ciscms.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
public class OfficeHoursBlock implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @ToString.Exclude
    private Instructor instructor;

    @DateTimeFormat(pattern = "kk:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "kk:mm")
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Override
    public String toString() {
        return dayOfWeek.toString() + " " + startTime.format(formatter) + " - " + endTime.format(formatter);
    }

}
