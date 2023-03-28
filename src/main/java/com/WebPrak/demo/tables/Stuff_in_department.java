package com.WebPrak.demo.tables;

import lombok.*;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Stuff_in_department")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Stuff_in_department{
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    @NonNull
    private Integer department_id;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stuff_id")
    @ToString.Exclude
    @NonNull
    private Integer stuff_id;

}