import ProjectService from '../services/ProjectService';
import { useNavigate, useParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';

function SaveProject() {
    const [name, setName] = useState('');

    const params = useParams();
    const id = params ? params.id : null;

    const navigate = useNavigate();

    useEffect(() => {
        if (id) {
            ProjectService.getProjectById(id).then((res) =>{
                let project = res.data;
                setName(project.name);
            });
        }        
    }, []);

    const saveProject = () => {
        let project = {id: id, name: name};

        if (id) {
            ProjectService.updateProject(project);
        } else {
            ProjectService.createProject(project);  
        }
        navigate("/projects");
    }

    const changeNameHandler= (event) => {
        setName(event.target.value);
    }

    const resolveTitle = () => {
        if (id) {
            return <h3 className="text-center">Update Project</h3>
        } else {
            return <h3 className="text-center">Add Project</h3>
        }
    }

    return (
        <div>
            <br></br>
                <div className = "container">
                    <div className = "row">
                        <div className = "card col-md-6 offset-md-3 offset-md-3">
                            <div className = "form-header">
                                { resolveTitle() }
                            </div>
                            <div className = "card-body">
                                <form>
                                    <div className = "form-group">
                                        <label> Name: </label>
                                        <input placeholder="Name" name="name" className="form-control" 
                                            value={name} onChange={changeNameHandler}/>
                                    </div>
                                    <div className = "form-result-buttons">
                                        <button className="btn btn-primary" onClick={() => saveProject()}>Save</button>
                                        <button className="btn btn-secondary" onClick={() => navigate("/projects")} style={{marginLeft: "10px"}}>Cancel</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
        </div>
    )
}

export default SaveProject
