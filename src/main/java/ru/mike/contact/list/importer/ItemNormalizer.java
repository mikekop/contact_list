package ru.mike.contact.list.importer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@Transactional
public class ItemNormalizer {
    @Autowired
    private EntityManager entityManager;

    public void normalize() {
        // TODO place normalization logic here
    }
}
