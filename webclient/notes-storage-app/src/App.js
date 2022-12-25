import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
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
    <>
        <Header />
        <div className="main-content">
          <Routes>
            <Route component={ <Navigate to="/notes" /> } exact path="/" />
            <Route component={<ProtectedRoute component={ProjectListComponent} path = "/projects"/>}/>
            <Route component={<ProtectedRoute component={SaveProjectComponent} path = "/create-project"/>}/>
            <Route component={<ProtectedRoute component={SaveProjectComponent} path = "/edit-project/:id"/>}/>
            <Route component={<ProtectedRoute component={NoteListComponent} path = "/notes"/>}/>
            <Route component={<ProtectedRoute component={SaveNoteComponent} path = "/create-note"/>}/>
            <Route component={<ProtectedRoute component={SaveNoteComponent} path = "/edit-note/:id"/>}/>
            <Route component={Callback} exact path="/callback" />
          </Routes>
        </div>
      </>
  );
};

export default App;