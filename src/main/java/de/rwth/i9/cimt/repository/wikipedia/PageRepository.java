package de.rwth.i9.cimt.repository.wikipedia;

import org.springframework.data.repository.CrudRepository;

import de.rwth.i9.cimt.model.wikipedia.Page;

public interface PageRepository extends CrudRepository<Page, Integer> {

}
