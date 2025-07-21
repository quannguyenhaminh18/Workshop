package app.entity.subclass;

import app.entity.superclass.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Stage extends NamedEntity {
    @ManyToOne
    @JoinColumn(nullable = false)
    private Board board;

    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;
}
