package de.rwth.i9.cimt.evaluation;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import de.tudarmstadt.ukp.wikipedia.datamachine.domain.JWPLDataMachine;

public class CIMTJWPLDataMachine {

	public static void main(String[] args)
			throws SAXNotRecognizedException, SAXNotSupportedException, ParserConfigurationException {
		System.setProperty("jdk.xml.totalEntitySizeLimit", "500000000");
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
		String[] arg = { "english", "Contents", "Disambiguation_pages",
				"C:\\rks\\Thesis\\Softwares\\Wikipedia dump\\JWPLDataMachine\\enwiki" };
		JWPLDataMachine.main(arg);

	}

}
