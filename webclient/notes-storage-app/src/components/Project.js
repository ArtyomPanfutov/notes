import React from 'react';
import { Button, Card } from 'react-bootstrap';

const Project = ({
  id,
  name,
  handleRemoveProject
}) => {
  return (
    <Card style={{ width: '18rem' }} className="project">
      <Card.Body>
        <Card.Title className="book-title">{name}</Card.Title>
        <Button variant="primary">Edit</Button>{' '}
        <Button variant="danger" onClick={() => handleRemoveProject(id)}>
          Delete
        </Button>
      </Card.Body>
    </Card>
  );
};

export default Project;