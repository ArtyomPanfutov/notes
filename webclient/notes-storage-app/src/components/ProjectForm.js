import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';

const ProjectForm = (props) => {
  const [project, setProject] = useState({
    projectName: props.project? props.project.projectName: ''
  });

  const [errorMsg, setErrorMsg] = useState('');
  const { projectName } = project;

  const handleOnSubmit = (event) => {
    event.preventDefault();
    const values = [ projectName ];
    let errorMsg = '';

    const allFieldsFilled = values.every((field) => {
      const value = `${field}`.trim();
      return value !== '' && value !== '0';
    });

    if (allFieldsFilled) {
      const project = {
        projectName,
      };
      props.handleOnSubmit(project);
    } else {
      errorMsg = 'Please fill out all the fields.';
    }
    setErrorMsg(errorMsg);
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    switch (name) {
      default:
        // Change to back-end call
        setProject((prevState) => ({
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
          <Form.Label>Project Name</Form.Label>
          <Form.Control
            className="input-control"
            type="text"
            name="projectName"
            value={projectName}
            placeholder="Enter project name"
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

export default ProjectForm;