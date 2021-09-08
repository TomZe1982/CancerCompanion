package de.tomze.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {

    @Column(name="street")
    private String street;

    @Column(name="number")
    private String number;

    @Column(name="city")
    private String city;

    @Column(name="zipCode")
    private String zipCode;


}
