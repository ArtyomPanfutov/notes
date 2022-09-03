import React from 'react';
import { Button, Card } from 'react-bootstrap';
import { useHistory } from 'react-router-dom';

const Project = ({
  id,
  name,
  handleRemoveProject
}) => {
  const history = useHistory();
  return (
    <Card style={{ width: '18rem' }} className="project">
      <Card.Body>
        <Card.Title className="project-title">{name}</Card.Title>
        <Button variant="primary" onClick={() => history.push(`/edit/${id}`)}>
           Edit
        </Button>
        <Button variant="danger" onClick={() => handleRemoveProject(id)}>
          Delete
        </Button>
      </Card.Body>
    </Card>
  );
};

export default Project;