import React, { useState } from 'react';
import ProjectForm from './ProjectForm';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

const AddProject = () => {
  const history = useHistory();
  const handleOnSubmit = (project) => {
     const name = project.projectName;
     axios.post("/api/projects", {
       name
     })
     history.push('/projects');
     history.go(0);
  };

  return (
    <React.Fragment>
      <ProjectForm handleOnSubmit={handleOnSubmit} />
    </React.Fragment>
  );
};

export default AddProject;