package com.WebPrak.demo.tables;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

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
    private Integer account_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    @NonNull
    private Integer department_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "op_type_id")
    @ToString.Exclude
    @NonNull
    private Integer op_type_id;

    @Column(name = "info")
    private String info;

}
