package app.entity.subclass;

import app.entity.superclass.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Comment extends AuditEntity {
    @Column(nullable = false, length = 100)
    private String content;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User author;
}
