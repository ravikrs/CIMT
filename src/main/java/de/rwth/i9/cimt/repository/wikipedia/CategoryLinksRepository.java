package de.rwth.i9.cimt.repository.wikipedia;

import org.springframework.data.repository.CrudRepository;

import de.rwth.i9.cimt.model.wikipedia.Categorylinks;
import de.rwth.i9.cimt.model.wikipedia.CategorylinksId;

public interface CategoryLinksRepository extends CrudRepository<Categorylinks, CategorylinksId> {

}
