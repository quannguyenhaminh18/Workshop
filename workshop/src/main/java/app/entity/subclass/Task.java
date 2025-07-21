package app.entity.subclass;

import app.entity.superclass.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class Task extends NamedEntity {
    private String description;
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn
    private User assignedTo;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Stage stage;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public enum TaskStatus {
        TODO, DOING, DONE
    }
}
