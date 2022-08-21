import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import axios from 'axios';

const NoteForm = (props) => {
  const [note, setNote] = useState({
    noteName: props.note? props.note.noteName: '',
    noteContent: props.note? props.note.noteContent: ''
  });

  const [errorMsg, setErrorMsg] = useState('');
  const { noteName } = note;
  const { noteContent } = note;

  const handleOnSubmit = (event) => {
    event.preventDefault();
    const values = [ noteName, noteContent ];
    let errorMsg = '';

    const allFieldsFilled = values.every((field) => {
      const value = `${field}`.trim();
      return value !== '' && value !== '0';
    });

    if (allFieldsFilled) {
      const note = {
        noteName,
        noteContent
      };
      props.handleOnSubmit(note);
    } else {
      errorMsg = 'Please fill out all the fields.';
    }
    setErrorMsg(errorMsg);
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    switch (name) {
      default:
        setNote((prevState) => ({
          ...prevState,
          [name]: value
        }));
    }
  };

  return (
    <div className="main-form">
      {errorMsg && <p className="errorMsg">{errorMsg}</p>}
      <Form onSubmit={handleOnSubmit}>
        <Form.Group controlId="name">
          <Form.Label>Note Name</Form.Label>
          <Form.Control
            className="input-control"
            type="text"
            name="noteName"
            value={noteName}
            placeholder="Enter note name"
            onChange={handleInputChange}
          />
        </Form.Group>
        <Form.Group controlId="content">
          <Form.Label>Note Name</Form.Label>
          <Form.Control
            className="input-control"
            type="text"
            name="noteContent"
            value={noteContent}
            placeholder="Enter content"
            onChange={handleInputChange}
          />
        </Form.Group>
        <Button variant="primary" type="submit" className="submit-btn">
          Submit
        </Button>
      </Form>
    </div>
  );
};

export default NoteForm;
