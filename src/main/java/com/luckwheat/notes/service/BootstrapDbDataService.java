package com.luckwheat.notes.service;

import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.repository.ProjectRepository;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class BootstrapDbDataService {

    private static final String DEFAULT_PROJECT_NAME = "Personal";

    private final ProjectRepository projectRepository;

    @Inject
    public BootstrapDbDataService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @EventListener
    void onApplicationEvent(ServerStartupEvent event) {
        if (projectRepository.findByName(DEFAULT_PROJECT_NAME).isEmpty()) {
            log.info("Bootstrapping default project...");

            var project = new Project();
            project.setName(DEFAULT_PROJECT_NAME);

            projectRepository.save(project);
        }
    }
}
