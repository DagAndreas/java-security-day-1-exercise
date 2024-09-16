package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID;

	@Column
	private String name;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"user"})
	@JsonIgnore
	private List<VideoGame> loanedGames;
}
