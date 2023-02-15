package com.henry.model;

import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.YesNoConverter;
import org.hibernate.type.descriptor.java.BooleanJavaType;
import org.hibernate.usertype.StaticUserTypeSupport;

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
    //@JdbcTypeCode(SqlTypes.CHAR)
    //@JavaType(BooleanJavaType.class)
    //@Type(value = BooleanUserTypeSupport.class)
    private Boolean deleted;

}