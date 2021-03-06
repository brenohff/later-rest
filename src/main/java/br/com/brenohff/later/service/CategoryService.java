package br.com.brenohff.later.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brenohff.later.model.LTCategory;
import br.com.brenohff.later.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	public List<LTCategory> getCategories() {
		return categoryRepository.findAll();
	}

}
