package de.rwth.i9.cimt.repository.wikipedia;

import org.springframework.data.repository.CrudRepository;

import de.rwth.i9.cimt.model.wikipedia.Iwlinks;
import de.rwth.i9.cimt.model.wikipedia.IwlinksId;

public interface IwlinksRepository extends CrudRepository<Iwlinks, IwlinksId> {

}
