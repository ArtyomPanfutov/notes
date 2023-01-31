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
            window.location.reload();
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
            setCurrentPage(0);
            fetchNotes();
        }
    }

    return (
            <div className="container">
                 <div className = "row">
                    <div className ="col-4 col-sm-3 col-lg-2 col-xl-1 col-xxl-1 p-0 m-0">
                        <button className="btn btn-primary" onClick={() => navigate('/create-note')}> New Note</button>
                    </div>
                    <div className ="col-8 col-sm-9 col-lg-10 col-xl-11 col-xxl-11 p-0 m-0">
                        <input placeholder="Search notes" className="search-input right-aligned" onChange={event => findNotesByContent(event.target.value, 0)} />
                    </div>
                    {totalPages > 1 &&
                        <div className ="col-12 col-xs-12 col-sm-12 col-lg-12 col-xl-12 col-xxl-12 p-0 m-0">
                                <ReactPaginate
                                    breakLabel="..."
                                    nextLabel="Next >"
                                    onPageChange={handlePageClick}
                                    forcePage={currentPage}
                                    pageRangeDisplayed={5}
                                    pageCount={totalPages}
                                    previousLabel="< Previous"
                                    renderOnZeroPageCount={() => false}
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
                    }
                 </div>
                 <br></br>
                 {isLoading && 
                    <div className="page-layout">
                        <Loader />
                    </div>
                 }
                 <div className = "row">
                        <table className = "table table-default notes-table">

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
                                            <div className="modal fade" id={`deleteModal${note.id}`} tabIndex="-1" aria-labelledby={`deleteModal${note.id}`} aria-hidden="true">
                                                <div className="modal-dialog">
                                                    <div className="modal-content">
                                                    <div className="modal-header">
                                                        <h1 className="modal-title fs-5" id="deleteModal">Delete note</h1>
                                                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div className="modal-body">
                                                        Are you sure you want to delete the note "{note.name}"?
                                                    </div>
                                                    <div className="modal-footer">
                                                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal" onClick={() => deleteNote(note.id)}>Delete</button>
                                                    </div>
                                                    </div>
                                                </div>
                                            </div>
                                        <tr key = {note.id}>
                                            <td> {note.id} </td>   
                                            <td> {note.name} </td>   
                                            <td> {note.contentPreview} </td>   
                                            <td>
                                                <div className="container">
                                                    <div className = "row  justify-content-center">
                                                        <div className ="col-12 col-xs-12 col-sm-12 col-lg-6 col-xl-6 col-xxl-6 p-0 m-0">
                                                            <button onClick={ () => navigate(`/edit-note/${note.id}`)} className="btn btn-edit">
                                                                <img src="/edit.png" width="20px" />
                                                            </button>
                                                        </div>
                                                        <div className ="col-12 col-xs-12 col-sm-12 col-lg-6 col-xl-6 col-xxl-6 p-0 m-0">
                                                            <button data-bs-toggle="modal" data-bs-target={`#deleteModal${note.id}`} className="btn btn-danger">
                                                                <img src="/delete.png" width="20px" />
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
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