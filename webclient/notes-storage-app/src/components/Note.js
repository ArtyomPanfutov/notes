import React from 'react';
import { Button, Card } from 'react-bootstrap';

const Note = ({
  id,
  name,
  content,
  projectName,
  handleRemoveNote
}) => {
  return (
    <Card style={{ width: '18rem' }} className="note">
      <Card.Body>
        <Card.Title className="name-title">{name}</Card.Title>
            <div className="note-details">
              <div>{content}</div>
            </div>
        <Button variant="primary">Edit</Button>{' '}
        <Button variant="danger" onClick={() => handleRemoveNote(id)}>
          Delete
        </Button>
      </Card.Body>
    </Card>
  );
};

export default Note;
