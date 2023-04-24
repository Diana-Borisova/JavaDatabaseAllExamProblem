package exam.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "laptops")
public class Laptop extends BaseEntity{

    @Size(min=8)
    @Column(unique = true, nullable = false)
    private String macAddress;

    @Positive
    @Column( nullable = false)
    private double cpuSpeed;

    @Min(8)
    @Max(128)
    @Column( nullable = false)
    private int ram;

    @Min(128)
    @Max(1024)
    @Column( nullable = false)
    private int storage;

    @Size(min=10)
    @Column(nullable = false,columnDefinition ="TEXT" )
    private String description;

    @Positive
    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WarrantyType warrantyType;

    @ManyToOne(optional = false)
    private Shop shop;

}
