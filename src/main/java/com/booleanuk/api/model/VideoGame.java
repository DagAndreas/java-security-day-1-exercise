package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data

@Entity
@Table(name="videogames")
public class VideoGame {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String title;

	@Column
	private String gamestudio;

	@Column
	private int ageRating;

	@Column
	private int numberOfPlayers;

	@Column
	private String genre;

	@ManyToOne
	@JoinColumn(name="userID")
	@JsonIgnoreProperties(value = {"videogames"})
	@JsonIgnore
	private User user;
}
