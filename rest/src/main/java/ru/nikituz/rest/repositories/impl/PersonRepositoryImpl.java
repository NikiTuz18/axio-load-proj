package ru.nikituz.rest.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.nikituz.rest.models.Person;
import ru.nikituz.rest.repositories.PersonRepository;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    private final SessionFactory sessionFactory;

    @Override
    public void save(Person person) {
        currentSession().persist(person);
    }

    private Session currentSession(){
        return sessionFactory.getCurrentSession();
    }
}
