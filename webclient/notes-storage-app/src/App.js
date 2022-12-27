import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
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
      <Router>
        <Header />
        <div className="main-content">
          <Routes>
            <Route element={ <Navigate to="/notes" /> } exact path="/" />
            <Route element={Callback} exact path="/callback" />
            <Route element={<ProtectedRoute/>}>
              <Route element={<ProjectListComponent/>} path = "/projects"/>
              <Route element={<SaveProjectComponent/>} exact path = "/create-project" />
              <Route element={<SaveProjectComponent/>} exact path = "/edit-project/:id" />
              <Route element={<NoteListComponent/>} path = "/notes" />
              <Route element={<SaveNoteComponent/>} exact path = "/create-note" />
              <Route element={<SaveNoteComponent/>} exact path = "/edit-note/:id"/>
            </Route>
          </Routes>
        </div>
      </Router>
    </>
  );
};

export default App;