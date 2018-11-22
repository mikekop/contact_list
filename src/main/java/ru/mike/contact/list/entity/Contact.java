package ru.mike.contact.list.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Contact implements Serializable {
    //I do not want to use lombock
    @Id
    @GeneratedValue
    private long id;
    @Column( name = "P_NAME")
    private String name;
    private String url;
    private boolean dublicate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDublicate() {
        return dublicate;
    }

    public void setDublicate(boolean dublicate) {
        this.dublicate = dublicate;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) &&
                Objects.equals(url, contact.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
