package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;
import java.time.LocalDate;

@Entity
@Table(name = "Operation")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Operations implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "operation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @ToString.Exclude
    @NonNull
    private Accounts account_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    @NonNull
    private Departments department_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "op_type_id")
    @ToString.Exclude
    @NonNull
    private Operation_type op_type_id;

    @Column(nullable = false, name = "date")
    @NonNull
    private LocalDate date;

    @Column(nullable = false, name = "amount")
    @NonNull
    private Float amount;


}
