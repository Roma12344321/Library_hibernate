package com.example.library.services;

import com.example.library.models.Person;
import com.example.library.repositories.PersonRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PersonRepository personRepository;

    private final EntityManager entityManager;

    @Autowired
    public PeopleService(PersonRepository personRepository, EntityManager entityManager) {
        this.personRepository = personRepository;
        this.entityManager = entityManager;
    }


    public List<Person> index() {
        Set<Person> people = new HashSet<>(entityManager.createQuery("SELECT p from Person p left join fetch p.bookList", Person.class).getResultList());
        for (Person person : people)
            System.out.println("Name: " + person.getName() + " Goods: " + person.getBookList());
        return new ArrayList<>(people);
    }

    public Person show(int id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person show(String name) {
        return personRepository.findByName(name);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        Person personToBeUpdated = personRepository.findById(id).orElse(null);
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setYear(person.getYear());
        personRepository.save(personToBeUpdated);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }
}
