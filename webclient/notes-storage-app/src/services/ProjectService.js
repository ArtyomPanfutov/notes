import httpClient from './HttpClient';

class ProjectService {
   
    createProject(project) {
        return httpClient.post("/api/projects", {
            "name": project.name
          });
    }

    updateProject(project) {
        return httpClient.put(`/api/projects/`, {
           "id": project.id,
           "name": project.name 
        });
    }

    getProjectsPage(currentPage) {
        return httpClient.get(`/api/projects/pageable/?page=${currentPage}&pageSize=5`);
    }

    getProjects() {
        return httpClient.get(`/api/projects`);
    }

    getProjectById(id) {
        return httpClient.get(`/api/projects/${id}`);
    }
    
    getProjectByName(name) {
        return httpClient.get(`/api/projects/name/${name}`);
    }

    getDefaultProject() {
        return httpClient.get('/api/projects/default');
    }

    deleteProjectById(id) {
        return httpClient.delete(`/api/projects/${id}`);
    }

}

export default new ProjectService();