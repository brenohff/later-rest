package br.com.brenohff.later.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.brenohff.later.models.LTUser;
import br.com.brenohff.later.service.UserService;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin
public class UserController {

	@Autowired
	UserService service;

	@RequestMapping(value = "/getAll")
	public List<LTUser> getAllUsers() {
		return service.getAll();
	}

	@RequestMapping(value = "/getUser")
	public LTUser getByFaceID(@RequestParam(value="id") String id) {
		return service.getUserByID(id);
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public void saveUser(@RequestBody LTUser user) {
		service.saveUser(user);
	}

}
