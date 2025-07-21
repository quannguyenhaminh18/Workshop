package app.entity.subclass;

import app.entity.superclass.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Board extends NamedEntity {
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stage> stages;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Workspace workspace;
}