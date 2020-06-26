package me.sangoh.demoinflearnrestapi.accounts;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter @Setter
@Entity
public class Account {

    @Id @GeneratedValue
    private Integer id;

    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<AccountRole> roles;

}
