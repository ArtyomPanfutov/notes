import ProjectService from '../services/ProjectService';
import ReactPaginate from "react-paginate";
import { useNavigate } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import Loader from './Loader';

function ProjectList() {
    const [projects, setProjects] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [isLoading, setIsLoading] = useState(true);

    const navigate = useNavigate();

    useEffect(() => {
        fetchProjects();
    }, [currentPage]);

    const deleteProject = (id) => {
        ProjectService.deleteProjectById(id).then( res => {
            // TODO: response handling
            setProjects(projects.filter(project => project.id !== id));
            window.location.reload();
        });
    }

    const fetchProjects = () => {
        setIsLoading(true);
        ProjectService.getProjectsPage(currentPage).then((res) => {
            setProjects(res.data.items);
            setTotalPages(res.data.pages);
            setIsLoading(false);
        });
    }

    const handlePageClick = (event) => {
        setCurrentPage(event.selected);
    };

    const navigateToEditProject = (id) => {
        navigate(`/edit-project/${id}`);
    }

    return (
        <div className = "container projects-container">
            <div className = "row">
                <div className ="col-12 col-xs-6 col-sm-6 col-lg-6 col-xl-8 col-xxl-8">
                    <button className="btn btn-primary" onClick={() => navigate("/create-project")}>New Project</button>
                </div>
                <div className ="col-12 col-xs-6 col-sm-6 col-lg-6 col-xl-4 col-xxl-4">
                    <ReactPaginate
                        breakLabel="..."
                        nextLabel="Next"
                        onPageChange={handlePageClick}
                        pageRangeDisplayed={5}
                        pageCount={totalPages}
                        previousLabel="Previous"
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
            {isLoading && 
                <div className="page-layout">
                    <Loader />;
                </div>
            }
            <div className = "row">
                <table className = "table table-default projects-table">

                    <thead>
                        <tr>
                            <th> ID </th>
                            <th> Project name</th>
                            <th> Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        { 
                            projects && 
                                projects.map(
                                    project => 
                                    <>
                                        <div class="modal fade" id={`deleteModal${project.id}`} tabindex="-1" aria-labelledby={`deleteModal${project.id}`} aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5" id="deleteModal">Delete project</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    Are you sure you want to delete the project "{project.name}"?
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal" onClick={() => deleteProject(project.id)}>Delete</button>
                                                </div>
                                                </div>
                                            </div>
                                        </div>
                                        <tr key = {project.id}>
                                            <td> {project.id} </td>   
                                            <td> {project.name} </td>   
                                            <td>
                                                <div className = "row">
                                                    <div className ="col-12 col-xs-12 col-sm-12 col-lg-12 col-xl-12 col-xxl-6">
                                                        <button onClick={() => navigateToEditProject(project.id)} className="btn btn-info">Update </button>
                                                    </div>
                                                    <div className ="col-12 col-xs-12 col-sm-12 col-lg-12 col-xl-12 col-xxl-6">
                                                        <button data-bs-toggle="modal" data-bs-target={`#deleteModal${project.id}`} className="btn btn-danger">Delete </button>
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
    )
}

export default ProjectList