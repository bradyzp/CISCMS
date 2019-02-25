package net.nsnsns.ciscms.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String title;
    private String firstName;
    private String lastName;
    private String phone;
    private String officeLocation;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<OfficeHoursBlock> officeHoursList;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Course> courses;
}
