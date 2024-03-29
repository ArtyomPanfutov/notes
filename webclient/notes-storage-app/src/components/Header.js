import React from 'react';
import { NavLink } from 'react-router-dom';
import AuthenticationButton from './AuthenticationButton'
import { useAuth0 } from "@auth0/auth0-react";

const Header = () => {
  const { isAuthenticated } = useAuth0();

  return (
    <header>
          <div className="auth-div">
            <AuthenticationButton />
          </div>
        <div className="container">
          <div className="header-text-div">
            <h1>Note App</h1>
          </div>
        </div>
        <hr/>
        <div className="links">
          {isAuthenticated && (
          <>
            <NavLink to="/projects" className="link" >
                Projects
            </NavLink>
            <NavLink to="/notes" className="link" >
              Notes
            </NavLink>
          </>
          )}
        </div>
    </header>
  );
};

export default Header;