package softuni.exam.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{

    @Positive
    @Column(nullable = false)
    private Double price;

    @Size(min=5)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String 	description;

    @Column(nullable = false)
    private boolean hasGoldStatus;

    @Column(nullable = false)
    private LocalDateTime addedOn;

    @ManyToOne(optional = false)
    private Car car;

    @ManyToOne(optional = false)
    private Seller seller;

    @ManyToMany
    @JoinTable(name = "offers_pictures",
    joinColumns = @JoinColumn(name = "offer_id"),
    inverseJoinColumns = @JoinColumn(name = "picture_id"))
    private List<Picture> pictures;

}
