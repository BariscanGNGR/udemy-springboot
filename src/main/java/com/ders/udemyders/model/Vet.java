package com.ders.udemyders.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_vet")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Vet extends BaseEntity {
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;
}