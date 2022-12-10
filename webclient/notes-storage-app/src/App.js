import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import Header from './components/Header';
import ProjectListComponent from './components/ProjectListComponent';
import SaveProjectComponent from './components/SaveProjectComponent';
import NoteListComponent from './components/NoteListComponent';
import SaveNoteComponent from './components/SaveNoteComponent';
import Loader from './components/Loader';
import Callback from './components/Callback';
import { useAuth0 } from '@auth0/auth0-react';
import { addAccessTokenInterceptor } from './services/HttpClient';
import { useEffect } from 'react';

const App = () => {

  const { isLoading } = useAuth0();
  const { getAccessTokenSilently } = useAuth0();

  useEffect(() => {
    addAccessTokenInterceptor(getAccessTokenSilently);
  }, [getAccessTokenSilently]);

  if (isLoading) {
    return (
      <div className="page-layout">
        <Loader />;
      </div>
    );
  }

  return (
    <BrowserRouter>
        <Header />
        <div className="main-content">
          <Switch>
            <ProtectedRoute component={ProjectListComponent} path="/projects" exact={true} />
            <ProtectedRoute component={SaveProjectComponent} path="/create-project" />
            <ProtectedRoute component={SaveProjectComponent} path="/edit-project/:id" />
            <ProtectedRoute component={NoteListComponent} path="/notes" exact={true} />
            <ProtectedRoute component={SaveNoteComponent} path="/create-note" />
            <ProtectedRoute component={SaveNoteComponent} path="/edit-note/:id" />
            <Route component={Callback} path="/callback" />
          </Switch>
        </div>
    </BrowserRouter>
  );
};

export default App;