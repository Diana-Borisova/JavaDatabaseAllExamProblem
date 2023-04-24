package softuni.exam.instagraphlite.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post extends BaseEntity{

    @Size(min = 21)
    @Column(nullable = false)
    private String caption;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "picture_id")
    private Picture picture;



}
