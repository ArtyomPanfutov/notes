import axios from 'axios';

class NotesService {
   
    createNote(note) {
        return axios.post("/api/notes", {
            "name": note.name,
            "content": note.content,
            "projectId": note.projectId
          });
    }

    updateNote(note) {
        return axios.put("/api/notes", {
            "id": note.id,
            "name": note.name,
            "content": note.content,
            "projectId": note.projectId
          });
    }

    getNotes() {
        return axios.get("/api/notes");
    }

    getNoteById(id) {
        return axios.get(`/api/notes/${id}`)
    }

    deleteNotetById(id) {
        return axios.delete(`/api/notes/${id}`)
    }

    findByContent(content) {
        return axios.post("/api/notes/search", {
            "content": content
          });
    }

    generateName() {
        return axios.get("/api/notes/name/new/generate");
    }
}

export default new NotesService();