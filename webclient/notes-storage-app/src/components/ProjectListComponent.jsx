import React, { Component } from 'react';
import ProjectService from '../services/ProjectService';
import ReactPaginate from "react-paginate";

class ProjectListComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                projects: [],
                currentPage: 0,
                totalPages: 0
        }
        this.addProject = this.addProject.bind(this);
        this.editProject = this.editProject.bind(this);
        this.deleteProject = this.deleteProject.bind(this);
    }

    deleteProject(id) {
        ProjectService.deleteProjectById(id).then( res => {
            this.setState({projects: this.state.projects.filter(project=> project.id !== id)});
        });
    }

    editProject(id) {
        this.props.history.push(`/edit-project/${id}`);
    }

    componentDidMount() {
        this.fetchProjects();
    }

    fetchProjects() {
        ProjectService.getProjectsPage(this.state.currentPage).then((res) => {
            this.setState({ projects: res.data.items, totalPages: res.data.pages });
        });
    }

    addProject() {
        this.props.history.push('/create-project');
    }

    handlePageClick = (event) => {
        this.setState({ currentPage: event.selected });
        this.fetchProjects();
    };

    render() {
        return (
            <div>
                 <div className = "row">
                     <div>
                    <button className="btn btn-primary" onClick={this.addProject}> New Project</button>
                    </div>
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
                                    <th> Project name</th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                { 
                                    this.state.projects && 
                                        this.state.projects.map(
                                            project => 
                                            <tr key = {project.id}>
                                                <td> {project.id} </td>   
                                                <td> {project.name} </td>   
                                                <td>
                                                    <button onClick={ () => this.editProject(project.id)} className="btn btn-info">Update </button>
                                                    <button style={{marginLeft: "10px"}} onClick={ () => this.deleteProject(project.id)} className="btn btn-danger">Delete </button>
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

export default ProjectListComponent