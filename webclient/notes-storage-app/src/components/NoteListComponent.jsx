import React, { Component } from 'react'
import NoteService from '../services/NoteService'

class NoteListComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                notes: []
        }
        this.addNote = this.addNote.bind(this);
        this.editNote = this.editNote.bind(this);
        this.deleteNote = this.deleteNote.bind(this);
    }

    deleteNote(id) {
        NoteService.deleteNoteById(id).then( res => {
            this.setState({notes: this.state.notes.filter(note => note.id !== id)});
        });
    }

    editNote(id) {
        this.props.history.push(`/edit-note/${id}`);
    }

    componentDidMount() {
        NoteService.getNotes().then((res) => {
            this.setState({ notes: res.data});
        });
    }

    addNote() {
        this.props.history.push('/create-note');
    }

    render() {
        return (
            <div>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.addNote}> Add Note</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> ID </th>
                                    <th> Name</th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.notes.map(
                                        note => 
                                        <tr key = {note.id}>
                                             <td> {note.id} </td>   
                                             <td> {note.name} </td>   
                                             <td>
                                                 <button onClick={ () => this.editNote(note.id)} className="btn btn-info">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteNote(note.id)} className="btn btn-danger">Delete </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default NoteListComponent 