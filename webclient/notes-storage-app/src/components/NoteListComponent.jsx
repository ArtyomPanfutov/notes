import React, { useEffect, useState } from 'react'
import NoteService from '../services/NoteService'
import ReactPaginate from "react-paginate";
import { useNavigate } from 'react-router-dom';

function NoteListComponent() {
    const [searchContent, setSearchContent] = useState(null);
    const [notes, setNotes] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        fetchNotes();
    });

    const navigate = useNavigate();

    const fetchNotes = () => {
        NoteService.getNotes(currentPage).then((res) => {
            setNotes(res.data.items);
            setTotalPages(res.data.pages);
        });
    }

    const handlePageClick = (event) => {
        setCurrentPage(event.selected);
        if (searchContent) {
            findNotesByContent(searchContent, currentPage);
        } else {
            fetchNotes();
        }
    };

    const deleteNote = (id) => {
        NoteService.deleteNoteById(id).then(res => {
            setNotes(notes.filter(note => note.id !== id));
        });
    }

    const findNotesByContent = (content, page) => {
        const trimmed = content.trim();
        if (trimmed) {
            NoteService.findByContent(trimmed, page).then(res => {
                setNotes(res.data.items ? res.data.items : []);
                setTotalPages(res.data.pages);
                setSearchContent(trimmed);
            })
        } else {
            setSearchContent(null);
            fetchNotes();
        }
    }

        return (
            <div>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={() => navigate('/create-note')}> New Note</button>
                    <input placeholder="Search notes" className="search-input" onChange={event => findNotesByContent(event.target.value, 0)} />
                    <div className="pagination">
                        <ReactPaginate
                            breakLabel="..."
                            nextLabel="Next >"
                            onPageChange={handlePageClick}
                            pageRangeDisplayed={5}
                            pageCount={totalPages}
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
                                    notes && notes.map(
                                        note => 
                                        <tr key = {note.id}>
                                            <td> {note.id} </td>   
                                            <td> {note.name} </td>   
                                            <td>
                                                <button onClick={ () => navigate(`/edit-note/${note.id}`)} className="btn btn-info">Update </button>
                                                <button style={{marginLeft: "10px"}} onClick={ () => deleteNote(note.id)} className="btn btn-danger">Delete </button>
                                            </td>
                                        </tr>
                                    )
                                    }
                            </tbody>
                        </table>

                 </div>

            </div>
        );
}

export default NoteListComponent