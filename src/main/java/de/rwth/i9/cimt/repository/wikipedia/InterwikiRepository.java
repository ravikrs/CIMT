package de.rwth.i9.cimt.repository.wikipedia;

import org.springframework.data.repository.CrudRepository;

import de.rwth.i9.cimt.model.wikipedia.Interwiki;
import de.rwth.i9.cimt.model.wikipedia.InterwikiId;

public interface InterwikiRepository extends CrudRepository<Interwiki, InterwikiId> {

}
