import Axios from './Axios';

class ProjectService {
   
    createProject(project) {
        return Axios.instance.post("/api/projects", {
            "name": project.name
          });
    }

    updateProject(project) {
        return Axios.instance.put(`/api/projects/`, {
           "id": project.id,
           "name": project.name 
        });
    }

    getProjects() {
        return Axios.instance.get("/api/projects");
    }

    getProjectById(id) {
        return Axios.instance.get(`/api/projects/${id}`);
    }
    
    getProjectByName(name) {
        return Axios.instance.get(`/api/projects/name/${name}`);
    }

    getDefaultProject() {
        return Axios.instance.get('/api/projects/name/Personal');
    }

    deleteProjectById(id) {
        return Axios.instance.delete(`/api/projects/${id}`);
    }

}

export default new ProjectService();