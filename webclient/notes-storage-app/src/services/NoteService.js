import httpClient from './HttpClient';

class NotesService {
   
    createNote(note) {
        return httpClient.post("/api/notes", {
            "name": note.name,
            "content": note.content,
            "projectId": note.projectId
          });
    }

    updateNote(note) {
        return httpClient.put("/api/notes", {
            "id": note.id,
            "name": note.name,
            "content": note.content,
            "projectId": note.projectId
          });
    }

    getNotes() {
        return httpClient.get("/api/notes");
    }

    getNoteById(id) {
        return httpClient.get(`/api/notes/${id}`)
    }

    deleteNotetById(id) {
        return httpClient.delete(`/api/notes/${id}`)
    }

    findByContent(content) {
        return httpClient.post("/api/notes/search", {
            "content": content
          });
    }

    generateName() {
        return httpClient.get("/api/notes/name/new/generate");
    }
}

export default new NotesService();