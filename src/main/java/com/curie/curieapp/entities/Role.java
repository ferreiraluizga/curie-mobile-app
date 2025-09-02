package com.curie.curieapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    public enum Values {
        ADMIN(1L), BASIC(2L);
        long roleId;
        Values(long roleId) {
            this.roleId = roleId;
        }
    }

}
