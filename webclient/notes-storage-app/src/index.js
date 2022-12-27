import React from 'react';
import ReactDOM from 'react-dom/client';
import 'semantic-ui-css/semantic.min.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import './styles.scss';
import App from './App';
import Auth0ProviderWrapper from './auth/Auth0ProviderWrapper';
import { BrowserRouter as Router } from 'react-router-dom';

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
      <Router>
        <Auth0ProviderWrapper>
          <App />
        </Auth0ProviderWrapper>
      </Router>
  </React.StrictMode>);

  
  // If you want your app to work offline and load faster, you can change
  // unregister() to register() below. Note this comes with some pitfalls.
  // Learn more about service workers: https://bit.ly/CRA-PWA
  //   serviceWorker.unregister();