package de.rwth.i9.cimt.repository.wikipedia;

import org.springframework.data.repository.CrudRepository;

import de.rwth.i9.cimt.model.wikipedia.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
