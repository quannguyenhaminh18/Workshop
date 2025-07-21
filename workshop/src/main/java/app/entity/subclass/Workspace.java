package app.entity.subclass;

import app.entity.superclass.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Workspace extends NamedEntity {
    @Column(nullable = false, unique = true, length = 10)
    private String joinCode;

    @Column(nullable = false)
    private boolean isPublic;

    @ManyToOne
    @JoinColumn
    private User manager;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable
    private Set<User> members;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards;
}