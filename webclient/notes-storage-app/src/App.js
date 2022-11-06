import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Header from './components/Header';
import ProjectListComponent from './components/ProjectListComponent';
import SaveProjectComponent from './components/SaveProjectComponent';
import NoteListComponent from './components/NoteListComponent';
import SaveNoteComponent from './components/SaveNoteComponent';

const App = () => {
  return (
    <BrowserRouter>
      <div>
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
      </div>
    </BrowserRouter>
  );
};

export default App;