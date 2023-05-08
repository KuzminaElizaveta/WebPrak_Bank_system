package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "Account")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Accounts implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "account_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @NonNull
    private Clients client_id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "acc_type_id")
    @ToString.Exclude
    @NonNull
    private Account_type acc_type_id;

    @Column(nullable = false, name = "balance")
    @NonNull
    private Float balance;

    @Column(nullable = false, name = "credit")
    @NonNull
    private LocalDate credit;

    @Column(nullable = false, name = "percent")
    @NonNull
    private Float percent;

    @Column(nullable = false, name = "interval")
    @NonNull
    private Integer interval;

    @Column(nullable = false, name = "period")
    @NonNull
    private Integer period;

    @OneToMany(mappedBy = "account_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Operations> clients = new ArrayList<>();
}
