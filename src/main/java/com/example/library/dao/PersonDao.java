package com.example.library.dao;

import com.example.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }

    public Person show(int person_id) {
        return jdbcTemplate.query(
                        "SELECT * FROM person WHERE person_id=?",
                        new Object[]{person_id},
                        new PersonMapper()
                )
                .stream().findAny().orElse(null);
    }

    public Person show(String name) {
        return jdbcTemplate.query(
                        "SELECT * FROM person WHERE name=?",
                        new Object[]{name},
                        new PersonMapper()
                )
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name,year) values(?,?)",
                person.getName(), person.getYear());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?,year=? WHERE person_id=?",
                updatedPerson.getName(), updatedPerson.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }
}