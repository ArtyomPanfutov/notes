import React, { useState } from 'react';
import ProjectForm from './ProjectForm';
import axios from 'axios';

const AddProject = () => {
  const handleOnSubmit = (project) => {
     const name = project.projectName;
     axios.post("/api/projects", {
       name
     })
  };

  return (
    <React.Fragment>
      <ProjectForm handleOnSubmit={handleOnSubmit} />
    </React.Fragment>
  );
};

export default AddProject;