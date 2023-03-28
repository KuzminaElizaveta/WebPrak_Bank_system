package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;
import java.sql.Date;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @NonNull
    private Long client_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acc_type_id")
    @ToString.Exclude
    @NonNull
    private Long acc_type_id;

    @Column(nullable = false, name = "balance")
    @NonNull
    private Float balance;

    @Column(nullable = false, name = "credit")
    @NonNull
    private Date credit;

    @Column(nullable = false, name = "percent")
    @NonNull
    private Float percent;

    @Column(nullable = false, name = "interval")
    @NonNull
    private Integer interval;

    @Column(nullable = false, name = "period")
    @NonNull
    private Integer period;
}
