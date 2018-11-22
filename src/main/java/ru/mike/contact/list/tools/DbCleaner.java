package ru.mike.contact.list.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mike.contact.list.dao.ContactListRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class DbCleaner {
    @Autowired
    private ContactListRepository contactListRepository;
    public void clean() {
        contactListRepository.deleteAll();
    }
}
