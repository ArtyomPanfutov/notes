import React from 'react';
import _ from 'lodash';
import Project from './Project';


const ProjectList = () => {
  const [data, setData] = React.useState(null);

  const handleRemoveProject = (id) => {
   // TODO: DELETE PROJECT
    setData(data.filter((item) => item.id !== id));
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