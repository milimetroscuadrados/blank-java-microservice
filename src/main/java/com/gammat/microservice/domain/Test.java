package com.gammat.microservice.domain;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

/**
 * Created by alejandro on 23/05/17.
 */

@Entity
@Table(name="test")
public class Test {

    @ApiModelProperty(notes = "El ID del container, generado automáticamente", required = true)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) 
    private Long id;

    @ApiModelProperty(notes = "El nombre del test", required = true)
    @NotNull(message = "El atributo NAME es obligatorio")
    private String name;

    @ApiModelProperty(notes = "El valor del test", required = true)
    @NotNull(message = "El atributo VALUE es obligatorio")
    private String value;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
