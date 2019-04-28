package net.nsnsns.ciscms.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String email;
    private String title;
    private String firstName;
    private String lastName;
    private String phone;
    private String officeLocation;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OfficeHoursBlock> officeHoursList = new ArrayList<>();

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Course> courses;

    @ManyToOne
    @ToString.Exclude
    private Student owner;

    @Override
    public String toString() {

        return title + " " + lastName;
    }
}
