import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import NoteService from '../services/NoteService';
import ProjectService from '../services/ProjectService';
import ProjectDropdownComponent from './ProjectDropdownComponent'
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import Loader from './Loader';

function SaveNote() {
    const params = useParams();
    const id = params ? params.id : null;
    const [name, setName] = useState('');
    const [content, setContent] = useState('');
    const [projectId, setProjectId] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const { state } = useLocation(); // For the note list state
    const [editor, setEditor] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        setIsLoading(true);
        if (id) {
            NoteService.getNoteById(id).then((res) => {
                let note = res.data;
                setName(note.name);
                setContent(note.content);
                setProjectId(note.projectId);
                setIsLoading(false);
            });
        } else {
            NoteService.generateName().then((res) => {
                setName(res.data.name);
            }).then(() => {
                ProjectService.getDefaultProject().then((res) => {
                    setProjectId(res.data.id);
                    setIsLoading(false);
                });
            });

        }       
    }, []);

    const saveNote = () => {
        let note = {
            id: id, 
            name: name,
            content: editor.getData(),
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

    const navigateBackToNoteList = () => {
        navigate("/notes", {
            state: {
                searchContent: state.notesState.searchContent,
                notes: state.notesState.notes,
                currentPage: state.notesState.currentPage,
                totalPages: state.notesState.totalPages,
                isLoading: state.notesState.isLoading
            }
        });
    }

    return (
            <div>
                <br></br>
                   <div className = "container">
                        {isLoading 
                            ? <div className="page-layout">
                                <Loader />
                              </div>
                            : <div className = "row">
                                    <div className = "card col-md-12">
                                        <div className = "form-header">
                                            {resolveTitle()}
                                        </div>
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
                                                        onReady={ editor => { 
                                                            setEditor(editor);
                                                        } }
                                                        onChange={ ( event, editor ) => {
                                                        } }
                                                        onBlur={ ( event, editor ) => {
                                                        } }
                                                        onFocus={ ( event, editor ) => {
                                                        } }
                                                    />
                                                </div>
                                                <div className = "form-result-buttons">
                                                    <button className="btn btn-success" onClick={() => saveNote()}>Save</button>
                                                    <button className="btn btn-secondary" onClick={() => navigateBackToNoteList()} style={{marginLeft: "10px"}}>Cancel</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            }
                   </div>
            </div>
    );
}

export default SaveNote
