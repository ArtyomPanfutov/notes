import ProjectService from '../services/ProjectService';
import ReactPaginate from "react-paginate";
import { useNavigate } from 'react-router-dom';
import React, { useState, useEffect } from 'react';

function ProjectListComponent() {
    const [projects, setProjects] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const navigate = useNavigate();

    useEffect(() => {
        fetchProjects();
    }, []);

    const deleteProject = (id) => {
        ProjectService.deleteProjectById(id).then( res => {
            setProjects(projects.filter(project=> project.id !== id));
        });
    }

    const fetchProjects = () => {
        ProjectService.getProjectsPage(currentPage).then((res) => {
            setProjects(res.data.items);
            setTotalPages(res.data.pages);
        });
    }

    const handlePageClick = (event) => {
        setCurrentPage(event.selected);
        fetchProjects();
    };


    const navigateToEditProject = (id) => {
        navigate(`/edit-project/${id}`);
    }

    return (
        <div>
            <div className = "row">
                <div>
                <button className="btn btn-primary" onClick={() => navigate("/create-project")}>New Project</button>
                </div>
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
                            <th> Project name</th>
                            <th> Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        { 
                            projects && 
                                projects.map(
                                    project => 
                                    <tr key = {project.id}>
                                        <td> {project.id} </td>   
                                        <td> {project.name} </td>   
                                        <td>
                                            <button onClick={() => navigateToEditProject(project.id)} className="btn btn-info">Update </button>
                                            <button style={{marginLeft: "10px"}} onClick={() => deleteProject(project.id)} className="btn btn-danger">Delete </button>
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

export default ProjectListComponent