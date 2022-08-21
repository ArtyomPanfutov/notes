import React from 'react';
import _ from 'lodash';
import Project from './Project';
import axios from 'axios';


const ProjectList = () => {
  const [data, setData] = React.useState(null);

  const handleRemoveProject = (id) => {
     axios.delete("/api/projects/" + id)
       .then(() => {
         fetch("/api/projects")
           .then((res) => res.json())
           .then((data) => setData(data));
       })
  };

   React.useEffect(() => {
     fetch("/api/projects")
       .then((res) => res.json())
       .then((data) => setData(data));
   }, []);

  return (
    <React.Fragment>
      <div className="project-list">
        {!_.isEmpty(data) ? (
          data.map((item) => (
            <Project key={item.id} {...item}  handleRemoveProject={handleRemoveProject} />
          ))
        ) : (
          <p className="message">No projects available.</p>
        )}
      </div>
    </React.Fragment>  );

};

export default ProjectList;