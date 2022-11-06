import { React, Component } from 'react'
import { Dropdown } from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import ProjectService from '../services/ProjectService'

class ProjectDropdownComponent extends Component {
  constructor(props) {
    super(props)
    this.state = {
      options: []
    }
  }

  componentDidMount() {
    ProjectService.getProjects().then((res) => {
        const arr = [];

        res.data.forEach(function(item) { 
          arr.push({
            key: item.id,
            text: item.name,
            value: item.name
          });
        });

        this.setState({ options: arr });
    });
  }

  render() {
    return (
      <div>
        <Dropdown
          placeholder='Select Project'
          fluid
          selection
          options={this.state.options}
        />
      </div>
    ) 
  }
}

export default ProjectDropdownComponent
  
