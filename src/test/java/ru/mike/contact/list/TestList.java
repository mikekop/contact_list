package ru.mike.contact.list;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mike.contact.list.config.Config;
import ru.mike.contact.list.dao.ContactListRepository;
import ru.mike.contact.list.entity.Contact;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Config.class)
@ActiveProfiles({"test", "h2"})
@TestPropertySource(locations="classpath:test.properties")
public class TestList {

    @Autowired
    private ContactListRepository repo;

    @Test
    public void doImportTest() {
        long count = repo.count();
        assertEquals("Number of imported persons is incorrect", 10, count);
    }

    @Test
    public void checkUrlCorrectness() {
        List<Contact> contacts = repo.findAll();
        List filtered = contacts.stream().filter(cnt -> !cnt.getUrl().startsWith("http")).collect(Collectors.toList());
        assertEquals("Found incorrectly imported persons", 0, filtered.size());
    }

    @Test
    public void checkFilter() {
        String nameToTest = "Bumblebee Man";
        List<Contact> res = repo.findByName(nameToTest);
        assertEquals("Wrong result size. Obtaned " + res.size(), 1, res.size());
        assertTrue("Result size less then 1 ",res.size() > 0);
        assertEquals("Wrong name received", nameToTest, res.get(0).getName());
    }

    @Test // Will test dedublicator
    public void checkDub() {
        repo.findAllUnique(PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "name"))));
    }
}
