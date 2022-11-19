import { React, Component } from 'react'
import { Dropdown } from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import ProjectService from '../services/ProjectService'

class ProjectDropdownComponent extends Component {
  constructor(props) {
    super(props)
    this.state = {
      options: [],
      defaultId: props.defaultId,
      onChange: props.onChange
    }
  }

  componentDidMount() {
    ProjectService.getProjects().then((res) => {
        const dropdownList = [];

        res.data.forEach(function(item) { 
          dropdownList.push({
            key: item.id,
            text: item.name,
            value: item.id
          });
        });

        this.setState({ options: dropdownList });
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
          defaultValue={this.state.defaultId}
          onChange={this.state.onChange}
        />
      </div>
    ) 
  }
}

export default ProjectDropdownComponent
  
