package com.example.football.models.dto;

import com.example.football.models.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerImportDto  {

    @Size(min=3)
    @XmlElement(name = "first-name")
    @NotNull
    private String firstName;

    @Size(min=3)
    @XmlElement(name = "last-name")
    @NotNull
    private String lastName;

    @Size(min=3)
    @XmlElement(name = "email")
    @NotNull
    private String email;

    @XmlElement(name = "birth-date")
    @NotNull
    private String birthDate;

    @XmlElement(name = "position")
    @NotNull
    private Positions position;

    @XmlElement(name = "town")
    @NotNull
    private PlayerTownImportDto town;

    @XmlElement(name = "stat")
    @NotNull
    private PlayerStatImportDto stat;

    @XmlElement(name = "team")
    @NotNull
    private PlayerTeamImportDto team;

}
