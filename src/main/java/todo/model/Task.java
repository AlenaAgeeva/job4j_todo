package todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @EqualsAndHashCode.Include
    private String title;
    @EqualsAndHashCode.Include
    private String description;
    public LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}