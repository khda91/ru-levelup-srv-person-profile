package ru.levelp.srv.person.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private UUID id;
    private String email;
    private String phoneNumber;
    private Role role;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private String placeOfWork;
    private Integer passportSeries;
    private Integer passportNumber;
    private String placeOfIssue;
    private LocalDate dateOfIssue;
    private String departmentCode;
    private String street;
    private Integer houseNumber;
    private Integer houseBuilding;
    private Character houseLetter;
    private Integer flat;
    private String city;
    private Integer postalCode;

    public enum Role {
        STUDENT,
        LECTOR,
        WORK_INSPECTOR,
        ADMINISTRATOR
    }
}
