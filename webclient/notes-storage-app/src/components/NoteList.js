import React from 'react';
import _ from 'lodash';
import Note from './Note';
import axios from 'axios';


const NoteList = () => {
  const [data, setData] = React.useState(null);

  const handleRemoveNote = (id) => {
     axios.delete("/api/notes/" + id)
       .then(() => {
         fetch("/api/notes/")
           .then((res) => res.json())
           .then((data) => setData(data));
       })
  };

  React.useEffect(() => {
    fetch("/api/notes/")
      .then((res) => res.json())
      .then((data) => setData(data));
  }, []);

  return (
    <React.Fragment>
      <div className="notes-list">
        {!_.isEmpty(data) ? (
          data.map((item) => (
            <Note key={item.id} {...item}  handleRemoveNote={handleRemoveNote} />
          ))
        ) : (
          <p className="message">No notes available.</p>
        )}
      </div>
    </React.Fragment>  );

};

export default NoteList;
