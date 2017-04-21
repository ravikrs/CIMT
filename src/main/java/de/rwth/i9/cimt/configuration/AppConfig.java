package de.rwth.i9.cimt.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.common.base.Predicates;

import de.rwth.i9.cimt.algorithm.similarity.lsr.CimtWordNetResource;
import de.tudarmstadt.ukp.dkpro.lexsemresource.exception.LexicalSemanticResourceException;
import de.tudarmstadt.ukp.wikipedia.api.DatabaseConfiguration;
import de.tudarmstadt.ukp.wikipedia.api.WikiConstants.Language;
import de.tudarmstadt.ukp.wikipedia.api.Wikipedia;
import de.tudarmstadt.ukp.wikipedia.api.exception.WikiInitializationException;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ComponentScan({ "de.rwth.i9.cimt.**", "com.sharethis.textrank" })
@PropertySource("classpath:opennlp.properties")
@Lazy(true)
@EnableJpaRepositories(basePackages = "de.rwth.i9.cimt.model.**")
@EnableSwagger2
public class AppConfig {

	private @Value("${cimt.en.wn}") String wordNetPath;

	private @Value("${cimt.home}") String cimtHome;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public Docket swaggerSettings() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(Predicates.not(PathSelectors.regex("/error.*"))).build().apiInfo(apiInfo()).pathMapping("/");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Semantic Keyterm Extraction Toolkit - SKET API")
				.description("Rest API can be used to extract keyphrases/keywords")
				.contact("Ravi" + " https://learntech.rwth-aachen.de/" + " ravi.singh@rwth-aachen.de")
				.license("Apache License Version 2.0")
				.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE").version("1.0").build();
	}

	@Bean
	public Wikipedia getWikipedia() {
		// configure the database connection parameters
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		dbConfig.setHost("localhost");
		dbConfig.setDatabase("simplewikidb");
		dbConfig.setUser("wikiuser");
		dbConfig.setPassword("wikiuser");
		dbConfig.setLanguage(Language.simple_english);

		// Create a new German wikipedia.
		Wikipedia wiki = null;
		try {
			wiki = new Wikipedia(dbConfig);
		} catch (WikiInitializationException e) {
			e.printStackTrace();
		}

		return wiki;
	}

	@Bean
	public CimtWordNetResource getCimtWordNetResource() {
		CimtWordNetResource wordNetResource = null;
		try {
			wordNetResource = new CimtWordNetResource(cimtHome + "src/main/resources/en/wordnet3.0");
		} catch (LexicalSemanticResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wordNetResource;
	}

}
