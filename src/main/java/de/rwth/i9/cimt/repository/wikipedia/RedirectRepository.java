package de.rwth.i9.cimt.repository.wikipedia;

import org.springframework.data.repository.CrudRepository;

import de.rwth.i9.cimt.model.wikipedia.Redirect;

public interface RedirectRepository extends CrudRepository<Redirect, Integer> {

}
