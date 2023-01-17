import React, { useEffect, useState } from 'react';
import NoteService from '../services/NoteService';
import ReactPaginate from "react-paginate";
import { useNavigate } from 'react-router-dom';
import Loader from './Loader';

function NoteList() {
    const [searchContent, setSearchContent] = useState(null);
    const [notes, setNotes] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [isLoading, setIsLoading] = useState(true);
    const [showDeleteDialog, setShowDeleteDialog] = useState(false);

    useEffect(() => {
        if (searchContent) {
            findNotesByContent(searchContent, currentPage);
        } else {
            fetchNotes();
        }
    }, [currentPage]);

    const navigate = useNavigate();

    const fetchNotes = () => {
        setIsLoading(true);
        NoteService.getNotes(currentPage).then((res) => {
            setNotes(res.data.items);
            setTotalPages(res.data.pages);
            setIsLoading(false);
        });
    }

    const handlePageClick = (event) => {
        setCurrentPage(event.selected);
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
                     <div className = "vertical-element">
                        <button className="btn btn-primary" onClick={() => navigate('/create-note')}> New Note</button>
                        <input placeholder="Search notes" className="search-input" onChange={event => findNotesByContent(event.target.value, 0)} />
                    </div>
                    <div className = "vertical-element">
                        <div className="right-aligned">
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
                 </div>
                 <br></br>
                 {isLoading && 
                    <div className="page-layout">
                        <Loader />;
                    </div>
                 }
                 <div className = "row">
                        <table className = "table table-striped table-bordered notes-table">

                            <thead>
                                <tr>
                                    <th> ID </th>
                                    <th> Name</th>
                                    <th> Preview</th> 
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    notes && notes.map(
                                        note =>  
                                        <>
                                            <div class="modal fade" id={`deleteModal${note.id}`} tabindex="-1" aria-labelledby={`deleteModal${note.id}`} aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5" id="deleteModa">Delete note</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        Are you sure you want delete the note?
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="button" class="btn btn-danger" onClick={() => deleteNote(note.id)}>Delete</button>
                                                    </div>
                                                    </div>
                                                </div>
                                            </div>
                                        <tr key = {note.id}>
                                            <td> {note.id} </td>   
                                            <td> {note.name} </td>   
                                            <td> {note.contentPreview} </td>   
                                            <td>
                                                <button onClick={ () => navigate(`/edit-note/${note.id}`)} className="btn btn-info">Update </button>
                                                <button style={{marginLeft: "10px"}} data-bs-toggle="modal" data-bs-target={`#deleteModal${note.id}`} className="btn btn-danger">Delete </button>
                                            </td>
                                        </tr>
                                        </>
                                    )
                                    }
                            </tbody>
                        </table>

                 </div>

            </div>
    );
}

export default NoteList