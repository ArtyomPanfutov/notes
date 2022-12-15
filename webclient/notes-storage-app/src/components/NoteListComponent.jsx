import React, { Component } from 'react'
import NoteService from '../services/NoteService'
import ReactPaginate from "react-paginate";

class NoteListComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                notes: [],
                currentPage: 0,
                totalPages: 0
        }
        this.addNote = this.addNote.bind(this);
        this.editNote = this.editNote.bind(this);
        this.deleteNote = this.deleteNote.bind(this);
    }

    deleteNote(id) {
        NoteService.deleteNoteById(id).then(res => {
            this.setState({notes: this.state.notes.filter(note => note.id !== id)});
        });
    }

    editNote(id) {
        this.props.history.push(`/edit-note/${id}`);
    }

    componentDidMount() {
        this.fetchNotes();
    }

    fetchNotes() {
        NoteService.getNotes(this.state.currentPage).then((res) => {
            this.setState({ notes: res.data.items, totalPages: res.data.pages });
        });
    }

    handlePageClick = (event) => {
        this.state.currentPage = event.selected;
        this.fetchNotes();
    };

    addNote() {
        this.props.history.push('/create-note');
    }

    findNotesByContent(content) {
        const trimmed = content.trim();
        if (trimmed) {
            NoteService.findByContent(trimmed).then(res => {
                this.setState({notes: res.data});
            })
        } else {
            this.fetchNotes();
        }
    }

    render() {
        return (
            <div>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.addNote}> New Note</button>
                    <input placeholder="Search notes" className="search-input" onChange={event => this.findNotesByContent(event.target.value)} />
                    <div className="pagination">
                        <ReactPaginate
                            breakLabel="..."
                            nextLabel="Next >"
                            onPageChange={this.handlePageClick}
                            pageRangeDisplayed={5}
                            pageCount={this.state.totalPages}
                            previousLabel="< Previous"
                            renderOnZeroPageCount={null}
                            breakClassName={"page-item"}
                            breakLinkClassName={"page-link"}
                            containerClassName={"pagination"}
                            pageClassName={"page-item"}
                            pageLinkClassName={"page-link"}
                            previousClassName={"page-item"}
                            previousLinkClassName={"page-link"}
                            nextClassName={"page-item"}
                            nextLinkClassName={"page-link"}
                            activeClassName={"active"}
                        />
                </div>
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