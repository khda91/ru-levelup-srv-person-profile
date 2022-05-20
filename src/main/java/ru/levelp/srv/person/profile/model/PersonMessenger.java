package ru.levelp.srv.person.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonMessenger {

    private UUID id;
    private UUID personId;
    private String messengerId;
    private String nickname;
}
