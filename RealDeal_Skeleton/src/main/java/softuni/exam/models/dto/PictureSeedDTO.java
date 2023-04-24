package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PictureSeedDTO {

    @Expose
    @Size(min = 2, max = 19)
    private String name;

    @Expose
    private String dateAndTime;

    @Expose
    private Integer car;
}