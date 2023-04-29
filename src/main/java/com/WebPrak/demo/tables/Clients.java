package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Client")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Clients implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "client_id")
    private Long id;

    @Column(nullable = false, name = "fullname")
    @NonNull
    private String fullname;

    @Column(name = "address")
    @NonNull
    private String address;

    @Column(name = "phone")
    @NonNull
    private String phone;

    @Column(nullable = false, name = "email")
    @NonNull
    private String email;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    @ToString.Exclude
    @NonNull
    private Client_type type_id;

    @Column(nullable = false, name = "birthday")
    @NonNull
    private Date birthday;

    @OneToMany(mappedBy = "client_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Accounts> accounts = new ArrayList<>();

}
