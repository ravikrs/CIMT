package de.rwth.i9.cimt.repository.wikipedia;

import org.springframework.data.repository.CrudRepository;

import de.rwth.i9.cimt.model.wikipedia.PagelinksId;

public interface PagelinksRepository extends CrudRepository<PagelinksRepository, PagelinksId> {

}
