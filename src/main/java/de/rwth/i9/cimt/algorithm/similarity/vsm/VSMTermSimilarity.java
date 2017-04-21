package de.rwth.i9.cimt.algorithm.similarity.vsm;

import java.io.File;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dkpro.similarity.algorithms.api.SimilarityException;
import dkpro.similarity.algorithms.api.TermSimilarityMeasure;
import dkpro.similarity.algorithms.vsm.InnerVectorProduct;
import dkpro.similarity.algorithms.vsm.NormalizedGoogleDistanceLikeComparator;
import dkpro.similarity.algorithms.vsm.VectorAggregation;
import dkpro.similarity.algorithms.vsm.VectorComparator;
import dkpro.similarity.algorithms.vsm.VectorNorm;
import dkpro.similarity.algorithms.vsm.store.IndexedDocumentsVectorReaderBase.WeightingModeIdf;
import dkpro.similarity.algorithms.vsm.store.IndexedDocumentsVectorReaderBase.WeightingModeTf;
import dkpro.similarity.algorithms.vsm.store.LuceneVectorReader;

public class VSMTermSimilarity {
	private static final Logger log = LoggerFactory.getLogger(VSMTermSimilarity.class);

	public double calculateVSMSimilarityMeasureNGD(String token1, String token2) {
		double relatednessScore = 0.0;
		LuceneVectorReader vSrc = new LuceneVectorReader(
				new File("C:\\rks\\Thesis\\DkproHome\\ESA\\LuceneIndexes\\Wikipedia"));
		vSrc.setVectorAggregation(VectorAggregation.CENTROID);
		vSrc.setWeightingThreshold(0.0f);
		vSrc.setVectorLengthThreshold(0.0f);
		vSrc.setWeightingModeTf(WeightingModeTf.binary);
		vSrc.setWeightingModeIdf(WeightingModeIdf.constantOne);
		vSrc.setNorm(VectorNorm.NONE);

		NormalizedGoogleDistanceLikeComparator ngdComparator = new NormalizedGoogleDistanceLikeComparator(vSrc);
		try {
			relatednessScore = ngdComparator.getSimilarity(token1, token2);
		} catch (SimilarityException e) {
			e.printStackTrace();
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return relatednessScore;
	}

	public double calculateVSMSimilarityMeasure(String token1, String token2) {
		double relatednessScore = 0;
		TermSimilarityMeasure c = getComparator(0.0f, 0.0f, WeightingModeTf.normal, WeightingModeIdf.normal,
				InnerVectorProduct.COSINE, VectorNorm.L2);
		try {
			relatednessScore = c.getSimilarity(token1, token2);
		} catch (SimilarityException e) {
			e.printStackTrace();
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return relatednessScore;
	}

	private TermSimilarityMeasure getComparator(float aWeightingThreshold, float aVectorLengthThreshold,
			WeightingModeTf aWeightingModeTf, WeightingModeIdf aWeightingModeIdf, InnerVectorProduct aInnerProduct,
			VectorNorm aNorm) {
		LuceneVectorReader vSrc = new LuceneVectorReader(
				new File("C:\\rks\\Thesis\\DkproHome\\ESA\\LuceneIndexes\\Wikipedia"));
		vSrc.setVectorAggregation(VectorAggregation.CENTROID);
		vSrc.setWeightingThreshold(aWeightingThreshold);
		vSrc.setVectorLengthThreshold(aVectorLengthThreshold);
		vSrc.setWeightingModeTf(aWeightingModeTf);
		vSrc.setWeightingModeIdf(aWeightingModeIdf);
		vSrc.setNorm(aNorm);

		VectorComparator cmp = new VectorComparator(vSrc);
		cmp.setInnerProduct(aInnerProduct);
		cmp.setNormalization(VectorNorm.NONE);
		return cmp;
	}
}
