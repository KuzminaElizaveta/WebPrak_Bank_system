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

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @NonNull
    private Clients client_id;

    @ManyToOne( fetch = FetchType.EAGER)
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
    private List<Operations> operations = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final Accounts other = (Accounts) obj;
        return  (this.id.equals(other.id)) &&
                (this.client_id.equals(other.client_id)) &&
                (this.acc_type_id.equals(other.acc_type_id)) &&
                (this.balance.equals(other.balance)) &&
                (this.credit.equals(other.credit)) &&
                (this.percent.equals(other.percent)) &&
                (this.interval.equals(other.interval)) &&
                (this.period.equals(other.period))
                ;
    }

}
