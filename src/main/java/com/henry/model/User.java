package com.henry.model;

import org.hibernate.type.YesNoConverter;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "deleted")
	@Convert(converter= YesNoConverter.class)
    private boolean deleted;
    @ManyToOne
    @JoinColumn(name = "home_address_id")
    private Address homeAddress;
    @ManyToOne
    @JoinColumn(name = "work_address_id")
    private Address workAddress;

}