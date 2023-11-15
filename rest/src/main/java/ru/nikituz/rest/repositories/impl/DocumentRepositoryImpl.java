package ru.nikituz.rest.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.nikituz.rest.models.Document;
import ru.nikituz.rest.repositories.DocumentRepository;

@Repository
@RequiredArgsConstructor
public class DocumentRepositoryImpl implements DocumentRepository {

    private final SessionFactory sessionFactory;

    @Override
    public void save(Document document) {
        currentSession().persist(document);
    }

    private Session currentSession(){
        return sessionFactory.getCurrentSession();
    }
}
