package com.WebPrak.demo.tables;

import com.WebPrak.demo.DAO.Stuff_in_departmentDAO;
import lombok.*;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Stuff")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Stuff implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "stuff_id")
    private Long id;

    @Column(nullable = false, name = "fullname")
    @NonNull
    private String fullname;

    @Column(nullable = false, name = "address")
    @NonNull
    private String address;

    @Column(nullable = false, name = "phone")
    @NonNull
    private String phone;

    @Column(nullable = false, name = "email")
    @NonNull
    private String email;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "job_title_id")
    @ToString.Exclude
    @NonNull
    private Job_titles job_title_id;

    @Column(nullable = false, name = "login")
    @NonNull
    private String login;

    @Column(nullable = false, name = "password")
    @NonNull
    private String password;

    @OneToOne(mappedBy = "stuff_id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Stuff_in_department Stuff_in_department;
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final Stuff other = (Stuff) obj;
        return  (this.id == other.id) &&
                (this.fullname.equals(other.fullname)) &&
                (this.address.equals(other.address)) &&
                (this.phone.equals(other.phone)) &&
                (this.email.equals(other.email)) &&
                (this.job_title_id == other.job_title_id) &&
                (this.login.equals(other.login)) &&
                (this.password.equals(other.password));
    }
}
