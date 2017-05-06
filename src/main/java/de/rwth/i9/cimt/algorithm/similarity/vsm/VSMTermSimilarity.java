package de.rwth.i9.cimt.algorithm.similarity.vsm;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rwth.i9.cimt.constants.SenseInventory;
import de.rwth.i9.cimt.util.ScoringUtil;
import de.rwth.i9.cimt.util.ScoringUtil.ScoringStrategy;
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
import dkpro.similarity.algorithms.vsm.store.vectorindex.VectorIndexReader;

public class VSMTermSimilarity {
	private static final Logger log = LoggerFactory.getLogger(VSMTermSimilarity.class);
	private SenseInventory senseInventory;
	private String esaVectorIndexPath;

	private VSMSimilarityAlgorithm similarityAlgorithm;
	private TermSimilarityMeasure similarityMeasure;

	public VSMTermSimilarity(SenseInventory si, String esaVectorIndexPath, VSMSimilarityAlgorithm similarityAlgorithm) {
		this.senseInventory = si;
		this.esaVectorIndexPath = esaVectorIndexPath;
		this.similarityAlgorithm = similarityAlgorithm;
		this.similarityMeasure = getESASimilarityMeasure(similarityAlgorithm);
	}

	public enum VSMSimilarityAlgorithm {
		ESA_NORMALIZED_GOOGLE_DISTANCE, ESA_COSINE_LOG_LOG_L2, ESA_COSINE_LOGPLUSONE_LOG_L2, ESA_COSINE_BIN_BIN_L2, ESA_COSINE_NORMAL_NORMAL_L2, ESA_LESK_NORMAL_NORMAL_L1, ESA_LESK_LOG_LOG_L1, ESA_LESK_LOGPLUSONE_LOG_L1, ESA_LESK_BIN_BIN_L1, ESA_MINOVERLAP_NORMAL_NORMAL_L1, ESA_MINOVERLAP_LOG_LOG_L2, ESA_MINOVERLAP_LOGPLUSONE_LOG_L2, ESA_MINOVERLAP_BIN_BIN_L1, ESA_AVGPROD_NORMAL_NORMAL_L2, ESA_AVGPROD_LOG_LOG_L2, ESA_AVGPROD_LOGPLUSONE_LOG_L2, ESA_LM_NORMAL_NORMAL_L2, ESA_LM_LOG_LOG_L2, ESA_LM_LOGPLUSONE_LOG_L2;
		public static VSMSimilarityAlgorithm fromString(String value) {
			if ("ESA_NORMALIZED_GOOGLE_DISTANCE".equalsIgnoreCase(value))
				return ESA_NORMALIZED_GOOGLE_DISTANCE;

			if ("ESA_COSINE_LOG_LOG_L2".equalsIgnoreCase(value))
				return ESA_COSINE_LOG_LOG_L2;

			if ("ESA_COSINE_LOGPLUSONE_LOG_L2".equalsIgnoreCase(value))
				return ESA_COSINE_LOGPLUSONE_LOG_L2;

			if ("ESA_COSINE_BIN_BIN_L2".equalsIgnoreCase(value))
				return ESA_COSINE_BIN_BIN_L2;

			if ("ESA_COSINE_NORMAL_NORMAL_L2".equalsIgnoreCase(value))
				return ESA_COSINE_NORMAL_NORMAL_L2;

			if ("ESA_LESK_NORMAL_NORMAL_L1".equalsIgnoreCase(value))
				return ESA_LESK_NORMAL_NORMAL_L1;

			if ("ESA_LESK_LOG_LOG_L1".equalsIgnoreCase(value))
				return ESA_LESK_LOG_LOG_L1;

			if ("ESA_LESK_LOGPLUSONE_LOG_L1".equalsIgnoreCase(value))
				return ESA_LESK_LOGPLUSONE_LOG_L1;

			if ("ESA_LESK_BIN_BIN_L1".equalsIgnoreCase(value))
				return ESA_LESK_BIN_BIN_L1;

			if ("ESA_MINOVERLAP_NORMAL_NORMAL_L1".equalsIgnoreCase(value))
				return ESA_MINOVERLAP_NORMAL_NORMAL_L1;

			if ("ESA_MINOVERLAP_LOG_LOG_L2".equalsIgnoreCase(value))
				return ESA_MINOVERLAP_LOG_LOG_L2;

			if ("ESA_MINOVERLAP_LOGPLUSONE_LOG_L2".equalsIgnoreCase(value))
				return ESA_MINOVERLAP_LOGPLUSONE_LOG_L2;

			if ("ESA_MINOVERLAP_BIN_BIN_L1".equalsIgnoreCase(value))
				return ESA_MINOVERLAP_BIN_BIN_L1;

			if ("ESA_AVGPROD_NORMAL_NORMAL_L2".equalsIgnoreCase(value))
				return ESA_AVGPROD_NORMAL_NORMAL_L2;

			if ("ESA_AVGPROD_LOGPLUSONE_LOG_L2".equalsIgnoreCase(value))
				return ESA_AVGPROD_LOGPLUSONE_LOG_L2;

			if ("ESA_LM_NORMAL_NORMAL_L2".equalsIgnoreCase(value))
				return ESA_LM_NORMAL_NORMAL_L2;

			if ("ESA_LM_LOG_LOG_L2".equalsIgnoreCase(value))
				return ESA_LM_LOG_LOG_L2;

			if ("ESA_LM_LOGPLUSONE_LOG_L2".equalsIgnoreCase(value))
				return ESA_LM_LOGPLUSONE_LOG_L2;

			if ("DEFAULT".equalsIgnoreCase(value))
				return ESA_COSINE_LOG_LOG_L2;

			return null;

		}
	}

	public double calculateVSMSimilarityMeasure(String token1, String token2) {
		double relatednessScore = 0;
		try {
			relatednessScore = similarityMeasure.getSimilarity(token1, token2);
		} catch (SimilarityException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return relatednessScore;
	}

	public double calculateVSMSimilarityMeasure(Collection<String> c1, Collection<String> c2) {
		double relatednessScore = 0;
		List<Double> relatednessValues = new ArrayList<>();
		for (String t1 : c1) {
			for (String t2 : c2) {
				relatednessValues.add(calculateVSMSimilarityMeasure(t1, t2));
			}
		}
		relatednessScore = ScoringUtil.getScore(ScoringStrategy.AVERAGE, relatednessValues);
		return relatednessScore;
	}

	private TermSimilarityMeasure getComparator(float aWeightingThreshold, float aVectorLengthThreshold,
			WeightingModeTf aWeightingModeTf, WeightingModeIdf aWeightingModeIdf, InnerVectorProduct aInnerProduct,
			VectorNorm aNorm) {
		VectorComparator esa = null;
		if (this.senseInventory.equals(SenseInventory.WIKIPEDIA)) {
			// we have already build lucene index for wikipedia so we can use
			// luceneVector index
			LuceneVectorReader vSrc = new LuceneVectorReader(new File(esaVectorIndexPath));
			vSrc.setVectorAggregation(VectorAggregation.CENTROID);
			vSrc.setWeightingThreshold(aWeightingThreshold);
			vSrc.setVectorLengthThreshold(aVectorLengthThreshold);
			vSrc.setWeightingModeTf(aWeightingModeTf);
			vSrc.setWeightingModeIdf(aWeightingModeIdf);
			vSrc.setNorm(aNorm);

			esa = new VectorComparator(vSrc);
			esa.setInnerProduct(aInnerProduct);
			esa.setNormalization(VectorNorm.NONE);

		} else {
			VectorIndexReader vSrc = new VectorIndexReader(new File(esaVectorIndexPath));
			// maybe experiment with VectorAggregation.SUM
			vSrc.setVectorAggregation(VectorAggregation.CENTROID);
			esa = new VectorComparator(vSrc);
			esa.setInnerProduct(aInnerProduct);
			esa.setNormalization(aNorm);
		}

		return esa;
	}

	private TermSimilarityMeasure getESASimilarityMeasure(VSMSimilarityAlgorithm vsmSimilarityAlgorithm) {
		TermSimilarityMeasure similarityMeasure = null;
		switch (vsmSimilarityAlgorithm) {
		case ESA_NORMALIZED_GOOGLE_DISTANCE:
			LuceneVectorReader vSrc = new LuceneVectorReader(new File(esaVectorIndexPath));
			vSrc.setVectorAggregation(VectorAggregation.CENTROID);
			vSrc.setWeightingThreshold(0.0f);
			vSrc.setVectorLengthThreshold(0.0f);
			vSrc.setWeightingModeTf(WeightingModeTf.binary);
			vSrc.setWeightingModeIdf(WeightingModeIdf.constantOne);
			vSrc.setNorm(VectorNorm.NONE);

			similarityMeasure = new NormalizedGoogleDistanceLikeComparator(vSrc);
			break;
		case ESA_COSINE_LOG_LOG_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.log, WeightingModeIdf.log,
					InnerVectorProduct.COSINE, VectorNorm.L2);
			break;

		case ESA_COSINE_LOGPLUSONE_LOG_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.logPlusOne, WeightingModeIdf.log,
					InnerVectorProduct.COSINE, VectorNorm.L2);
			break;

		case ESA_COSINE_BIN_BIN_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.binary, WeightingModeIdf.binary,
					InnerVectorProduct.COSINE, VectorNorm.L2);
			break;

		case ESA_COSINE_NORMAL_NORMAL_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.normal, WeightingModeIdf.normal,
					InnerVectorProduct.COSINE, VectorNorm.L2);
			break;

		case ESA_LESK_NORMAL_NORMAL_L1:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.normal, WeightingModeIdf.normal,
					InnerVectorProduct.LESK_OVERLAP, VectorNorm.L1);
			break;

		case ESA_LESK_LOG_LOG_L1:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.log, WeightingModeIdf.log,
					InnerVectorProduct.LESK_OVERLAP, VectorNorm.L2);
			break;

		case ESA_LESK_LOGPLUSONE_LOG_L1:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.logPlusOne, WeightingModeIdf.log,
					InnerVectorProduct.LESK_OVERLAP, VectorNorm.L2);
			break;

		case ESA_LESK_BIN_BIN_L1:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.binary, WeightingModeIdf.binary,
					InnerVectorProduct.LESK_OVERLAP, VectorNorm.L2);
			break;

		case ESA_MINOVERLAP_NORMAL_NORMAL_L1:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.normal, WeightingModeIdf.normal,
					InnerVectorProduct.MIN_OVERLAP, VectorNorm.L1);
			break;

		case ESA_MINOVERLAP_LOG_LOG_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.log, WeightingModeIdf.log,
					InnerVectorProduct.MIN_OVERLAP, VectorNorm.L2);
			break;

		case ESA_MINOVERLAP_LOGPLUSONE_LOG_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.logPlusOne, WeightingModeIdf.log,
					InnerVectorProduct.MIN_OVERLAP, VectorNorm.L2);
			break;

		case ESA_MINOVERLAP_BIN_BIN_L1:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.binary, WeightingModeIdf.binary,
					InnerVectorProduct.MIN_OVERLAP, VectorNorm.L1);
			break;

		case ESA_AVGPROD_NORMAL_NORMAL_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.normal, WeightingModeIdf.normal,
					InnerVectorProduct.AVERAGE_PRODUCT, VectorNorm.L2);
			break;

		case ESA_AVGPROD_LOG_LOG_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.log, WeightingModeIdf.log,
					InnerVectorProduct.AVERAGE_PRODUCT, VectorNorm.L2);
			break;

		case ESA_AVGPROD_LOGPLUSONE_LOG_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.logPlusOne, WeightingModeIdf.log,
					InnerVectorProduct.AVERAGE_PRODUCT, VectorNorm.L2);
			break;

		case ESA_LM_NORMAL_NORMAL_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.normal, WeightingModeIdf.normal,
					InnerVectorProduct.LANGUAGE_MODEL, VectorNorm.L2);
			break;

		case ESA_LM_LOG_LOG_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.log, WeightingModeIdf.log,
					InnerVectorProduct.LANGUAGE_MODEL, VectorNorm.L2);
			break;

		case ESA_LM_LOGPLUSONE_LOG_L2:
			similarityMeasure = getComparator(0.0f, 0.0f, WeightingModeTf.logPlusOne, WeightingModeIdf.log,
					InnerVectorProduct.LANGUAGE_MODEL, VectorNorm.L2);
			break;

		}
		return similarityMeasure;

	}

}
