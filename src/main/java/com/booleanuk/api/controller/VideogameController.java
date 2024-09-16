package com.booleanuk.api.controller;

import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.VideogameRepository;
import com.booleanuk.api.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequestMapping("videogames")
public class VideogameController {

	@Autowired
	private VideogameRepository videogameRepository;

	private HashMap<String, String> errorMessage;

	public VideogameController(VideogameRepository videogameRepository) {
		this.videogameRepository = videogameRepository;
		this.errorMessage = new HashMap<>();
		errorMessage.put("message", "Failed");
	}


	@GetMapping
	public ResponseEntity<ResponseObject<?>> getGames() {
		return new ResponseEntity<>(new ResponseObject<>("success", videogameRepository.findAll()), HttpStatus.OK);
	}


	public void checkIfValidVideoGame(VideoGame game) {
		try {
			if (game.getTitle() == null ||
					game.getGamestudio() == null ||
					game.getAgeRating() <= 0 ||
					game.getNumberOfPlayers() <= 0 ||
					game.getGenre() == null) {
				throw new Exception("Parse error found");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid request body");
		}
	}


	@PostMapping
	public ResponseEntity<ResponseObject<?>> postOne(@RequestBody VideoGame videoGame) {
		try {
			checkIfValidVideoGame(videoGame);
			// throws error if invalid.
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(new ResponseObject<>("failed", errorMessage), HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(new ResponseObject<>("Success", videogameRepository.save(videoGame)), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponseObject<?>> deleteOne(@PathVariable int id){
		VideoGame delGame;
		try{
			delGame = videogameRepository.findById(id)
					.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(new ResponseObject<>("failed", errorMessage), HttpStatus.NOT_FOUND);

		}

		videogameRepository.delete(delGame);
		return new ResponseEntity<>(new ResponseObject<>("Success", delGame), HttpStatus.OK);
	}


	@GetMapping("{id}")
	public ResponseEntity<ResponseObject<?>> getOne(@PathVariable int id){
		VideoGame found = videogameRepository.findById(id).orElse(null);
		if (found == null){
			return new ResponseEntity<>(new ResponseObject<>("failed", errorMessage), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ResponseObject<>("Success", found), HttpStatus.OK);

	}


}