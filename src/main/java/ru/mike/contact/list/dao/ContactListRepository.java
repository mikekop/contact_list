package ru.mike.contact.list.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mike.contact.list.entity.Contact;

import java.util.List;

@Repository
public interface ContactListRepository extends JpaRepository<Contact, Long> {
    // maybe more than one guy with the same name
    @Query("from Contact c where c.name like %?1% and c.dublicate=false")
    List<Contact> findByName(String name);

    @Query(value = "from Contact c where c.dublicate=false",
    countQuery = "select count(1) from Contact c where c.dublicate=false")
    Page<Contact> findAllUnique(PageRequest request);
}
