package de.rwth.i9.cimt.repository.wikipedia;

import org.springframework.data.repository.CrudRepository;

import de.rwth.i9.cimt.model.wikipedia.Text;

public interface TextRepository extends CrudRepository<Text, Integer> {

}
