import { useNavigate } from "react-router-dom";

const withNavigation = (Component) => {
    return props => <Component {...props} navigate={useNavigate()} />;
} 

export default withNavigation;