import React, { Component } from 'react'
import ProjectService from '../services/ProjectService'

class ProjectListComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                projects: []
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
        ProjectService.getProjects().then((res) => {
            this.setState({ projects: res.data});
        });
    }

    addProject() {
        this.props.history.push('/create-project');
    }

    render() {
        return (
            <div>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.addProject}> Add Project</button>
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