package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Department")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Departments implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "department_id")
    private Long id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "address")
    @NonNull
    private String address;

    @Column(nullable = false, name = "phone")
    @NonNull
    private String phone;

    @Column(nullable = false, name = "email")
    @NonNull
    private String email;

    @Column(nullable = false, name = "staff_count")
    @NonNull
    private Integer staff_count;

    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final Departments other = (Departments) obj;
        return  (this.id == other.id) &&
                (this.name.equals(other.name)) &&
                (this.address.equals(other.address)) &&
                (this.phone.equals(other.phone)) &&
                (this.email.equals(other.email)) &&
                (this.staff_count == other.staff_count);
    }

    @OneToMany(mappedBy = "department_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Operations> operations = new ArrayList<>();

    @OneToMany(mappedBy = "department_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Stuff_in_department> stuff_in_departments = new ArrayList<>();
}
