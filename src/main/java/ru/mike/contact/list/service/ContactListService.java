package ru.mike.contact.list.service;

import org.springframework.data.domain.Page;
import ru.mike.contact.list.entity.Contact;

public interface ContactListService {
    Page<Contact> getList(int pageNumber, int pageSieze, String sortField, boolean asc, String filter);
}
