import React, { Component } from 'react'
import NoteService from '../services/NoteService';
import ProjectDropdownComponent from './ProjectDropdownComponent'

class SaveNoteComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            name: '',
            content: '',
            projectId: null
        }
        this.chnageNameHandler = this.changeNameHandler.bind(this);
        this.chnageContentHandler = this.changeContentHandler.bind(this);
        this.chnageProjectIdHandler = this.changeProjectIdHandler.bind(this);
    }

    componentDidMount() {
        if (this.state.id) {
            NoteService.getNoteById(this.state.id).then((res) =>{
                let note = res.data;
                this.setState({
                    name: note.name,
                    content: note.content,
                    projectId: note.projectId
                });
            });
        }        
    }

    saveNote = (p) => {
        p.preventDefault();
        let note = {
            id: this.state.id, 
            name: this.state.name,
            content: this.state.content,
            projectId: this.state.projectId
        };

        if (this.state.id) {
            NoteService.updateNote(note).then( res => {
                this.props.history.push('/notes');
            });
        } else {
            NoteService.createNote(note).then(res => {
                this.props.history.push('/notes');
            });
        }
    }

    changeNameHandler = (event) => {
        this.setState({name: event.target.value});
    }

    changeContentHandler = (event) => {
        this.setState({content: event.target.value});
    }

    changeProjectIdHandler = (event) => {
        this.setState({projectId: event.target.value});
    }

    cancel() {
        this.props.history.push('/notes');
    }

    resolveTitle() {
        if (this.state.id) {
            return <h3 className="text-center">Update Note</h3>
        } else {
            return <h3 className="text-center">Add Note</h3>
        }
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.resolveTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> Name: </label>
                                            <input placeholder="Name" name="name" className="form-control" 
                                                value={this.state.name} onChange={this.chnageNameHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Content: </label>
                                            <input placeholder="Content" name="content" className="form-control" 
                                                value={this.state.content} onChange={this.chnageContentHandler}/>
                                        </div>
                                            <label> Project: </label>
                                            <ProjectDropdownComponent />
                                        <button className="btn btn-success" onClick={this.saveNote}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default SaveNoteComponent
