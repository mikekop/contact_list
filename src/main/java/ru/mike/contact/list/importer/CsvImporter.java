package ru.mike.contact.list.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mike.contact.list.entity.Contact;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.*;

@Service
@Transactional
public class CsvImporter implements Importer {
    private static final Logger logger = LoggerFactory.getLogger(CsvImporter.class);
    @Autowired
    private EntityManager entityManager;
    private String sourceUrl;

    @Override
    public void doImport() throws IOException {
        if (sourceUrl.startsWith("classpath")) {
            sourceUrl = sourceUrl.substring(sourceUrl.indexOf(":") + 1);
        }
        logger.debug("Source url is " + sourceUrl);
        InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(sourceUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String str;
        while ((str = br.readLine()) != null) {
            if (!str.startsWith("name")) {
                Contact contact = parseLine(str);
                logger.debug("Will persist contact " + contact);
                entityManager.persist(contact);
            }
        }
        entityManager.flush();
    }

    private Contact parseLine(String str) {
        Contact contact = new Contact();
        String[] spl = str.split(",");
        contact.setName(spl[0]);
        if (spl[1].trim().startsWith("http")) {
            contact.setUrl(spl[1].trim());
        } else {
            contact.setUrl(spl[2].trim());
            contact.setName(contact.getName() + ", " + spl[1]);
        }
        return contact;
    }

    @Override
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
