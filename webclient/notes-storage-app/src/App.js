import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Header from './components/Header';
import ProjectListComponent from './components/ProjectListComponent';
import SaveProjectComponent from './components/SaveProjectComponent';
import NoteListComponent from './components/NoteListComponent';
import SaveNoteComponent from './components/SaveNoteComponent';
import { useAuth0 } from '@auth0/auth0-react';

const App = () => {
  const { isLoading } = useAuth0();

  if (isLoading) {
    return <Loading />;
  }

  return (
    <BrowserRouter>
        <Header />
        <div className="main-content">
          <Switch>
            <Route component={ProjectListComponent} path="/projects" exact={true} />
            <Route component={SaveProjectComponent} path="/create-project" />
            <Route component={SaveProjectComponent} path="/edit-project/:id" />
            <Route component={NoteListComponent} path="/notes" exact={true} />
            <Route component={SaveNoteComponent} path="/create-note" />
            <Route component={SaveNoteComponent} path="/edit-note/:id" />
          </Switch>
        </div>
    </BrowserRouter>
  );
};

export default App;