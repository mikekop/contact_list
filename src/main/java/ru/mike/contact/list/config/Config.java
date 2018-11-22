package ru.mike.contact.list.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.mike.contact.list.importer.Dedublicator;
import ru.mike.contact.list.importer.Importer;
import ru.mike.contact.list.importer.ItemNormalizer;

import javax.persistence.EntityManager;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages = "ru.mike.contact.list")
public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    @Value("${initial.source.url}")
    private String initialSourceUrl;

    @Bean
    public Boolean dbChecker(
            @Autowired
                    EntityManager entityManager,
            @Autowired
                    Importer importer,
            @Autowired
                    Dedublicator dedublicator,
            @Autowired
                    ItemNormalizer normalizer) {
        long count = ((Long) entityManager.createQuery("select count(1) from Contact").getSingleResult());
        logger.info("Count is  " + count);
        if (count == 0) {
            importer.setSourceUrl(initialSourceUrl);
            try {
                importer.doImport();
                dedublicator.dedublicate();
                normalizer.normalize();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return true;
    }
}
