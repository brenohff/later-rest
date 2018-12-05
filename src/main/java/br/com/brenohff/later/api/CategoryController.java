package br.com.brenohff.later.api;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.brenohff.later.model.LTCategory;
import br.com.brenohff.later.service.CategoryService;
import br.com.brenohff.later.service.UserService;

@RestController
@RequestMapping(value = "/categories")
@CrossOrigin
public class CategoryController {

    @Autowired
    CategoryService service;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Busca todas as categorias.")
    @GetMapping(value = "/getAll")
    private ResponseEntity<List<LTCategory>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getCategories());
    }

}
