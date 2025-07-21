package app.entity.superclass;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class NamedEntity extends BaseEntity {
    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
