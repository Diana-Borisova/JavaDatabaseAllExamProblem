package softuni.exam.instagraphlite.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Size(min = 2, max = 18)
    @Column(unique = true, nullable = false)
    private String username;

    @Size(min = 4)
    @Column( nullable = false)
    private String password;


    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_picture_id")
    private Picture profilePicture;

    @OneToMany(mappedBy = "user", targetEntity = Post.class, fetch = FetchType.EAGER)
    private List<Post>posts;



}
