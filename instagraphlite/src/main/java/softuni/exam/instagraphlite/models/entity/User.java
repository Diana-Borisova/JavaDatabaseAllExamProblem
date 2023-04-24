package softuni.exam.instagraphlite.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Size(min = 2, max = 18)
    @Column(unique = true,nullable = false)
    private String username;

    @Size(min = 4)
    @Column(nullable = false)
    private String password;

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_picture_id")
    private Picture profilePicture;

    @OneToMany(mappedBy = "user", targetEntity = Post.class, fetch = FetchType.EAGER)
    private List<Post> posts;

    public User() {
        this.posts = new ArrayList<>();
    }

    public User(String username, String password, Picture profilePicture) {
        this();
        this.username = username;
        this.password = password;
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {

        return String.format("User: %s", username) + System.lineSeparator() +
                String.format("Post count: %d", posts.size()) + System.lineSeparator()+
                posts
                        .stream()
                        .sorted(Comparator.comparingDouble(p -> p.getPicture().getSize()))
                        .map(Post::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
    }
}
