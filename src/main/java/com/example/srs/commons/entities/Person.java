package com.example.srs.commons.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Person {

    private String firstName;

    @NotBlank(message = "Họ không được để trống")
    private String lastName;
    private String phone;
    private String address;
    private String avatarURL;

    public String getFullName() {
        return String.format("%s %s",firstName,lastName);
    }

}
