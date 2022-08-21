import React, { useState } from 'react';
import NoteForm from './NoteForm';
import axios from 'axios';

const AddNote = () => {
  const handleOnSubmit = (note) => {
     const name = note.noteName;
     const content = note.noteContent;
     const projectId = 1; // TODO
     axios.post("/api/notes/", {
       name,
       content,
       projectId
     });
  };

  return (
    <React.Fragment>
      <NoteForm handleOnSubmit={handleOnSubmit} />
    </React.Fragment>
  );
};

export default AddNote;
