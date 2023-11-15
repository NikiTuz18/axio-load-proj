package ru.nikituz.rest.repositories;

import ru.nikituz.rest.models.Document;

public interface DocumentRepository {
    void save(Document document);
}
