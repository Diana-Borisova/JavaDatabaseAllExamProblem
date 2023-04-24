package com.example.football.models.dto;

import com.example.football.models.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "stat")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatImportDto {

    @Positive
    @XmlElement(name = "shooting")
    @NotNull
    private float shooting;

    @Positive
    @XmlElement(name = "passing")
    @NotNull
    private float passing;

    @Positive
    @XmlElement(name = "endurance")
    @NotNull
    private float endurance;
}
