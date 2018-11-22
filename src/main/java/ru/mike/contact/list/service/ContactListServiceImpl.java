package ru.mike.contact.list.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.mike.contact.list.dao.ContactListRepository;
import ru.mike.contact.list.entity.Contact;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ContactListServiceImpl implements ContactListService {

    @Autowired
    private ContactListRepository repository;

    @Override
    public Page<Contact> getList(int pageNumber, int pageSize, String sortField, boolean asc, String filter) {
        if (filter != null && !filter.isEmpty()) {
            List<Contact> res = repository.findByName(filter);
            return new PageImpl<>(res);
        } else {
            if (sortField == null || sortField.isEmpty())
                sortField = "id";
            return repository.findAllUnique(PageRequest.of(pageNumber, pageSize, Sort.by(new Sort.Order(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortField))));

        }
    }
}
