package ru.mike.contact.list.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.mike.contact.list.entity.Contact;
import ru.mike.contact.list.service.ContactListServiceImpl;

import java.util.List;

@RestController
public class ContactListController {
    private static final Logger logger = LoggerFactory.getLogger(ContactListController.class);
    private static int DEFAULT_PAGE_SIZE = 10;
    @Autowired
    private ContactListServiceImpl service;

    @RequestMapping(value = "/list",
            params = {},
            method = RequestMethod.GET)
    public Page<Contact> getList(
            @RequestParam("page")
                    int pageNumber,
            @RequestParam("size")
                    int pageSize,
            @RequestParam("field")
                    String sortField,
            @RequestParam("asc")
                    boolean asc,
            @RequestParam(value = "filter", required = false)
                    String nameToFilter) {
        logger.debug("Name to filter is >" + nameToFilter + "<");
        return service.getList(pageNumber, pageSize != 0 ? pageSize : DEFAULT_PAGE_SIZE, sortField, asc, nameToFilter);
    }
}
