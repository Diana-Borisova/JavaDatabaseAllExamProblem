package com.likebookapp.model.service;

import com.likebookapp.model.entity.MoodsEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostServiceModel {

    private Long id;

    private String content;

    private MoodsEnum mood;
}
