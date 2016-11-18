package de.rwth.i9.cimt.service.kpextraction;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.rwth.i9.cimt.model.Keyword;

@Service("tprKPExtraction")
public class TPRKPExtraction {
	private static final Logger logger = LoggerFactory.getLogger(TPRKPExtraction.class);

	public List<Keyword> extractKeywordTPR() {
		return new ArrayList<Keyword>();
	}

}
