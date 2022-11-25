package com.luckwheat.notes.service;

import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.repository.ProjectRepository;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import static com.luckwheat.notes.service.ProjectService.DEFAULT_PROJECT_NAME;

@Singleton
@Slf4j
public class BootstrapDbDataService {

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
