package app.entity.subclass;

import app.entity.superclass.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseEntity {
    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private AuthInfo authInfo;

    @Column(length = 50)
    private String fullName;

    @Column(unique = true, length = 254)
    private String email;

    @Column(unique = true, length = 10)
    private String phone;

    @ManyToMany(mappedBy = "members")
    private List<Workspace> workspaces;
}
