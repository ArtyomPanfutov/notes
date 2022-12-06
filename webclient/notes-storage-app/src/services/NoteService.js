import Axios from './Axios';

class NotesService {
   
    createNote(note) {
        return Axios.instance.post("/api/notes", {
            "name": note.name,
            "content": note.content,
            "projectId": note.projectId
          });
    }

    updateNote(note) {
        return Axios.instance.put("/api/notes", {
            "id": note.id,
            "name": note.name,
            "content": note.content,
            "projectId": note.projectId
          });
    }

    getNotes() {
        return Axios.instance.get("/api/notes");
    }

    getNoteById(id) {
        return Axios.instance.get(`/api/notes/${id}`)
    }

    deleteNotetById(id) {
        return Axios.instance.delete(`/api/notes/${id}`)
    }

    findByContent(content) {
        return Axios.instance.post("/api/notes/search", {
            "content": content
          });
    }

    generateName() {
        return Axios.instance.get("/api/notes/name/new/generate");
    }
}

export default new NotesService();