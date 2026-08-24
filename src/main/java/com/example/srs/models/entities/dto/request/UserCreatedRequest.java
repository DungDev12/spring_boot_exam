package com.example.srs.models.entities.dto.request;

import com.example.srs.commons.entities.Person;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreatedRequest extends Person {

    private String username;
    private String password;
    private String email;

}
