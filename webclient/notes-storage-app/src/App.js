import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import Header from './components/Header';
import ProjectList from './components/ProjectList';
import SaveProject from './components/SaveProject';
import NoteList from './components/NoteList';
import SaveNote from './components/SaveNote';
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
        <Loader />
      </div>
    );
  }

  return (
    <>
        <Header />
        <div className="main-content">
          <Routes>
            <Route element={ <Navigate to="/notes" /> } exact path="/" />
            <Route element={<ProtectedRoute/>}>
              <Route element={<NoteList/>} exact path = "/notes" />
              <Route element={<ProjectList/>} exact path = "/projects"/>
              <Route element={<SaveProject/>} exact path = "/create-project" />
              <Route element={<SaveProject/>} exact path = "/edit-project/:id" />
              <Route element={<SaveNote/>} exact path = "/create-note" />
              <Route element={<SaveNote/>} exact path = "/edit-note/:id"/>
              <Route element={<Callback/>} path="/callback" />
            </Route>
          </Routes>
        </div>
    </>
  );
};

export default App;