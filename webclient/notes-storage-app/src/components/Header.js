import React from 'react';
import { NavLink } from 'react-router-dom';

const Header = () => {
  return (
    <header>
      <h1>Notes Storage App</h1>
      <hr />
      <div className="links">
        <NavLink to="/projects" className="link" activeClassName="active" exact>
            Projects
        </NavLink>
        <NavLink to="/notes" className="link" activeClassName="active" exact>
           Notes
        </NavLink>
      </div>
    </header>
  );
};

export default Header;