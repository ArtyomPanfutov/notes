import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Header from '../components/Header';
import AddProject from '../components/AddProject';
import ProjectList from '../components/ProjectList';

const AppRouter = () => {
  return (
    <BrowserRouter>
      <div>
        <Header />
        <div className="main-content">
          <Switch>
            <Route component={ProjectList} path="/projects" exact={true} />
            <Route component={AddProject} path="/create-project" />
          </Switch>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default AppRouter;