package com.example.Application.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Model of cat
 * Matches with table "cats" in database
 */
@Entity
@Table(name = "cats")
@Getter
@Setter
@NoArgsConstructor
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catid")
    private Integer catId;

    private String name;

    @Column(name = "dateofbirth")
    private Date dateOfBirth;

    private String breed;

    @Column(name = "colorid")
    private Integer colorId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerid")
    private Owner owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "catfriendship",
            joinColumns = @JoinColumn(name = "firstcatid"),
            inverseJoinColumns = @JoinColumn(name = "secondcatid")
    )
    private List<Cat> friends = new ArrayList<>();
}
