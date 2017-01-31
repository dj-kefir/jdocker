package ru.tutorials.jdocker.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Transient
    private String filter;

}
