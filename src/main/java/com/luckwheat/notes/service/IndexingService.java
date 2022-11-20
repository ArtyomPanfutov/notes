package com.luckwheat.notes.service;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Singleton
@Slf4j
public class IndexingService {

    private final EntityManager entityManager;

    public IndexingService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void initiateIndexing() {
        log.info("Initiating indexing...");
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            log.error("Indexing is interrupted");
            Thread.currentThread().interrupt();
        }

        log.info("Indexing has finished.");
    }
}
