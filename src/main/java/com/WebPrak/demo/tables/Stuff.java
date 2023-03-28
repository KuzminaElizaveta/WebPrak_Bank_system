package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;

import java.sql.Date;
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

    @Column(nullable = false, name = "info")
    @NonNull
    private String info;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_title_id")
    @ToString.Exclude
    @NonNull
    private Integer job_title_id;

    @Column(nullable = false, name = "login")
    @NonNull
    private String login;

    @Column(nullable = false, name = "password")
    @NonNull
    private String password;
}
