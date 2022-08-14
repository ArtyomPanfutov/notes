import React from 'react';
import ProjectForm from './ProjectForm';

const AddProject = () => {
  const handleOnSubmit = (project) => {
    console.log(project);
  };

  return (
    <React.Fragment>
      <ProjectForm handleOnSubmit={handleOnSubmit} />
    </React.Fragment>
  );
};

export default AddProject;