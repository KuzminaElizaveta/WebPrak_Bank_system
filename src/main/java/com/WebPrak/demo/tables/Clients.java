package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

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
    @Column(nullable = false, name = "client_id")
    private Long id;

    @Column(nullable = false, name = "fullname")
    @NonNull
    private String fullname;

    @Column(nullable = false, name = "info")
    @NonNull
    private String info;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    @ToString.Exclude
    @NonNull
    private Integer type_id;

    @Column(nullable = false, name = "birthday")
    @NonNull
    private Date birthday;

}
