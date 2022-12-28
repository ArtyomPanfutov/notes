import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import NoteService from '../services/NoteService';
import ProjectService from '../services/ProjectService';
import ProjectDropdownComponent from './ProjectDropdownComponent'
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

function SaveNote() {
    const params = useParams();
    const id = params ? params.id : null;
    const [name, setName] = useState('');
    const [content, setContent] = useState('');
    const [projectId, setProjectId] = useState(null);
    
    const navigate = useNavigate();

    useEffect(() => {
        if (id) {
            NoteService.getNoteById(id).then((res) => {
                let note = res.data;
                setName(note.name);
                setContent(note.content);
                setProjectId(note.projectId);
            });
        } else {
            NoteService.generateName().then((res) => {
                setName(res.data.name);
            });

            ProjectService.getDefaultProject().then((res) => {
                setProjectId(res.data.id);
            })
        }       
    }, []);

    const saveNote = () => {
        let note = {
            id: id, 
            name: name,
            content: content,
            projectId: projectId
        };

        if (id) {
            NoteService.updateNote(note);
        } else {
            NoteService.createNote(note);
        }
        navigate('/notes');
    }

    const changeNameHandler = (event) => {
        setName(event.target.value);
    }

    const changeProjectIdHandler = (e, data) => {
        setProjectId(data.value);
    }

    const resolveTitle = () => {
        if (id) {
            return <h3 className="text-center">Update Note</h3>
        } else {
            return <h3 className="text-center">Add Note</h3>
        }
    }

    return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-12">
                                {resolveTitle()}
                                <div className = "card-body">
                                    <form>
                                        <div className="form-row">
                                            <div className="col-md-4 mb-3">
                                                <label> Name: </label>
                                                <input placeholder="Name" name="name" className="form-control" 
                                                    value={name} onChange={changeNameHandler}/>
                                            </div>
                                            <div className="col-md-4 mb-3">
                                                <label> Project: </label>
                                                {projectId && (<ProjectDropdownComponent onChange={changeProjectIdHandler} defaultId={projectId} />)}
                                            </div>
                                        </div>
                                        <div className = "form-group">
                                            <label> Content: </label>
                                            <CKEditor
                                                editor={ClassicEditor}
                                                data={content}
                                                onReady={ editor => { } }
                                                onChange={ ( event, editor ) => {
                                                    setContent(editor.getData());
                                                } }
                                                onBlur={ ( event, editor ) => {
                                                } }
                                                onFocus={ ( event, editor ) => {
                                                } }
                                            />
                                        </div>
                                        <button className="btn btn-success" onClick={() => saveNote()}>Save</button>
                                        <button className="btn btn-danger" onClick={() => navigate('/notes')} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                   </div>
            </div>
    );
}

export default SaveNote
