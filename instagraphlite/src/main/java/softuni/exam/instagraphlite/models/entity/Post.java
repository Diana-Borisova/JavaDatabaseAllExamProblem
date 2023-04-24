package softuni.exam.instagraphlite.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
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




    @Override
    public String toString() {

        return "==Post Details: %s" + System.lineSeparator() +
                String.format("----Caption: %s", caption) + System.lineSeparator() +
                String.format("----Picture Size: %.2f", picture.getSize());
    }  }