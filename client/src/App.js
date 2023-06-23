import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import LandingPage from './components/LandingPage';
import LoginForm from './components/login/LoginForm';
import { useEffect, useState } from 'react';
import API from './API';
import RegistrationForm from './components/registration/SignUpForm';
import jwt from 'jwt-decode'

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


  useEffect(() => {
    const jwtToUser = (jwt) => {
      return {
        email: jwt.email,
        firstName: jwt.given_name,
        lastName: jwt.family_name,
        role: jwt.resource_access["authN"].roles[0]
      }
    } 

    const loggedInUser = localStorage.getItem("jwt");
    if (loggedInUser) {
      //updated asynchronously
      setUser(jwtToUser(jwt(loggedInUser)))
      setLoggedIn(true);
    }
  }, [])

  useEffect(() => { console.log(user) }, [user])

  const doLogIn = async (credentials) => {
    return API.logIn(credentials)
      .then(user => {
        setLoggedIn(true);
        setUser(user);
        navigate('/');
      })
  }

  const doSignup = async (credentials) => { 
    return API.signup(credentials)
      .then(() => { 
        doLogIn({username: credentials.username, password: credentials.password})
      })
  }

  const handleLogout = () =>  {
    setLoggedIn(false)
    setUser({})
    localStorage.removeItem("jwt")
    navigate('/')
}


  return (
    <Routes>
     <Route path="/" element={
          !loggedIn 
            ? <Navigate to="/login"/>
            : <LandingPage user={user} handleLogout={handleLogout}/>
        }></Route> 
      <Route path="/login" element={loggedIn ? <Navigate to="/" /> : <LoginForm login={doLogIn} />} />
      <Route path="/registration" element={loggedIn ? <Navigate to="/" /> : <RegistrationForm signup={doSignup}/>}/>
    </Routes>
  );
}

export default App;