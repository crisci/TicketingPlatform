import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import LandingPage from './components/LandingPage';
import LoginForm from './components/login/LoginForm';
import { useState } from 'react';
import API from './API';
import RegistrationForm from './components/registration/SignUpForm';


function App() {
  return (
    <>
      <Router>
        <MainApp />
      </Router>
    </>
  );
}

function MainApp(props) {

  const navigate = useNavigate();

  const [user, setUser] = useState({});
  const [loggedIn, setLoggedIn] = useState(false)

  const doLogIn = async (credentials) => {
    return API.logIn(credentials)
      .then(user => {
        console.log(user)
        setLoggedIn(true);
        setUser(user);
        navigate('/');
      })
  }


  return (
    <Routes>
     <Route path="/" element={
          !loggedIn 
            ? <Navigate to="/login"/>
            : <LandingPage/>
        }></Route> 
      <Route path="/login" element={loggedIn ? <Navigate to="/" /> : <LoginForm login={doLogIn} />} />
      <Route path="/registration" element={loggedIn ? <Navigate to="/" /> : <RegistrationForm login={doLogIn}/>}/>
    </Routes>
  );
}

export default App;