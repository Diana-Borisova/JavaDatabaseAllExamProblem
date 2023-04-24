package com.likebookapp.model.binding;

import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.MoodsEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AddPostBindingModel {
    @NotNull
    @Size(min = 2, max = 50, message = "Content length must be between 2 and 50 characters!")
    private String content;

    @NotNull(message = "You must select a mood!")
    private MoodsEnum mood;
}
