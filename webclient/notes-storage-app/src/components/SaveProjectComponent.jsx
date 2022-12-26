import React, { Component } from 'react'
import ProjectService from '../services/ProjectService';
import withNavigation from './hocs';

class SaveProjectComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match ? this.props.match.params.id : null,
            name: ''
        }
        this.chnageNameHandler = this.changeNameHandler.bind(this);
    }

    componentDidMount() {
        if (this.state.id) {
            ProjectService.getProjectById(this.state.id).then((res) =>{
                let project = res.data;
                this.setState({
                    name: project.name,
                });
            });
        }        
    }

    saveProject = (p) => {
        p.preventDefault();
        let project = {id: this.state.id, name: this.state.name};

        if (this.state.id) {
            ProjectService.updateProject(project).then( res => {
                this.props.navigate('/projects');
            });
        } else {
            ProjectService.createProject(project).then(res => {
                this.props.navigate('/projects');
            });
        }
    }

    changeNameHandler= (event) => {
        this.setState({name: event.target.value});
    }

    resolveTitle() {
        if (this.state.id) {
            return <h3 className="text-center">Update Project</h3>
        } else {
            return <h3 className="text-center">Add Project</h3>
        }
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.resolveTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> Name: </label>
                                            <input placeholder="Name" name="name" className="form-control" 
                                                value={this.state.name} onChange={this.chnageNameHandler}/>
                                        </div>
                                        <button className="btn btn-success" onClick={this.saveProject}>Save</button>
                                        <button className="btn btn-danger" onClick={this.props.navigate('/projects')} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default withNavigation(SaveProjectComponent)
