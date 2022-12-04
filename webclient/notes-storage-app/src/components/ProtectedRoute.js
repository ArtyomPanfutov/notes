import { withAuthenticationRequired } from "@auth0/auth0-react";
import React from "react";
import { Route } from "react-router-dom";
import Loader from "./Loader";

const ProtectedRoute = ({ component, ...args }) => (
  <Route
    component={withAuthenticationRequired(component, {
      onRedirecting: () => (
        <div className="page-layout">
          <Loader/>
        </div>
      ),
    })}
    {...args}
  />
);

export default ProtectedRoute;