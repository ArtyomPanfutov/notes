import React, {useState, useEffect} from 'react';
import ProjectForm from './ProjectForm';
import { useParams } from 'react-router-dom';
import { useHistory } from 'react-router-dom';
import axios from 'axios';

const EditProject = () => {
  const { id } = useParams();
  const history = useHistory();
  const [project, setProject] = useState(null);

  useEffect(() =>  {
    axios({url : "/api/projects/" + id, baseUrl : "/"})
      .then((response) => setProject(response.data));
  }, []);

  const handleOnSubmit = (project) => {
     const name = project.projectName;
     const id = project.id;
     axios.put("/api/projects", {
       id,
       name
     });
    history.push('/');
  };

  const data = {"id" :1, "name":"this is the name"}

  return (
        <div className = "project-form">
           <ProjectForm project={project} handleOnSubmit={handleOnSubmit} />
        </div>
  );
};

export default EditProject;