import axios from 'axios';

class ProjectService {
   
    createProject(project) {
        return axios.post("/api/projects", {
            "name": project.name
          });
    }

    updateProject(project) {
        return axios.put(`/api/projects/`, {
           "id": project.id,
           "name": project.name 
        });
    }

    getProjects() {
        return axios.get("/api/projects");
    }

    getProjectById(id) {
        return axios.get(`/api/projects/${id}`);
    }
    
    getProjectByName(name) {
        return axios.get(`/api/projects/name/${name}`);
    }

    getDefaultProject() {
        return axios.get('/api/projects/name/Personal');
    }

    deleteProjectById(id) {
        return axios.delete(`/api/projects/${id}`);
    }

}

export default new ProjectService();