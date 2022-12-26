import React, { Component } from 'react'
import NoteService from '../services/NoteService'
import ReactPaginate from "react-paginate";
import withNavigation from './hocs';

class NoteListComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                searchContent: null,
                notes: [],
                currentPage: 0,
                totalPages: 0
        }
        this.deleteNote = this.deleteNote.bind(this);
    }

    deleteNote(id) {
        NoteService.deleteNoteById(id).then(res => {
            this.setState({notes: this.state.notes.filter(note => note.id !== id)});
        });
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
        this.setState({ currentPage: event.selected });
        if (this.state.searchContent) {
            this.findNotesByContent(this.state.searchContent, this.state.currentPage);
        } else {
            this.fetchNotes();
        }
    };

    findNotesByContent(content, page) {
        const trimmed = content.trim();
        if (trimmed) {
            NoteService.findByContent(trimmed, page).then(res => {
                this.setState({
                    notes: res.data.items ? res.data.items : [], 
                    totalPages: res.data.pages, 
                    searchContent: trimmed
                });
            })
        } else {
            this.setState({ searchContent: null });
            this.fetchNotes();
        }
    }

    render() {
        return (
            <div>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.props.navigate('/create-note')}> New Note</button>
                    <input placeholder="Search notes" className="search-input" onChange={event => this.findNotesByContent(event.target.value, 0)} />
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
                                this.state.notes &&
                                    this.state.notes.map(
                                        note => 
                                        <tr key = {note.id}>
                                            <td> {note.id} </td>   
                                            <td> {note.name} </td>   
                                            <td>
                                                <button onClick={ () => this.props.navigate(`/edit-note/${note.id}`)} className="btn btn-info">Update </button>
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

export default withNavigation(NoteListComponent)