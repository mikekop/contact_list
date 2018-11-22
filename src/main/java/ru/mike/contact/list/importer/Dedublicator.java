package ru.mike.contact.list.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.mike.contact.list.entity.Contact;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
public class Dedublicator {
    private static final Logger logger = LoggerFactory.getLogger(Dedublicator.class);
    @Autowired
    private EntityManager entityManager;
    //We will not create unique index on table, because dedublication logic may be much more complicated. Now we simple check full coincidence on url and name
    //UPDATE. Found that many items actually duplicated i.e. logos and name itself are the same, but urls is different. I do not want to compare images :) So i assume that different urls means different images
    public void dedublicate() {
        Query q = entityManager.createNativeQuery("select count(1) from Contact c where c.p_name=:name and c.url=:url and c.dublicate=0 and c.id <> :id");
        List<Contact> res = entityManager.createQuery("from Contact c order by c.id", Contact.class).getResultList();
        for (Contact cnt : res) {
            BigInteger count = (BigInteger) q.setParameter("name", cnt.getName()).setParameter("url", cnt.getUrl()).setParameter("id", cnt.getId()).getSingleResult();
            if (count.intValue() > 0) {
                cnt.setDublicate(true);
                entityManager.merge(cnt);
                entityManager.flush();
            }
        }
    }
}
