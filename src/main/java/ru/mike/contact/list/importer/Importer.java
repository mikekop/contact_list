package ru.mike.contact.list.importer;

import javax.persistence.EntityManager;
import java.io.IOException;

public interface Importer {
    void doImport() throws IOException;
    void setSourceUrl(String sourceUrl);
}
