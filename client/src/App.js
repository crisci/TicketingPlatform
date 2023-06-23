import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import LandingPage from './components/LandingPage';
import LoginForm from './components/login/LoginForm';
import { useEffect, useState } from 'react';
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

  useEffect(() => {
    const loggedInUser = localStorage.getItem("jwt");
    if (loggedInUser) {
      const foundUser = JSON.parse(loggedInUser);
      console.log(foundUser)
      setUser(foundUser);
      setLoggedIn(true);
    }
  }, [])

  const doLogIn = async (credentials) => {
    return API.logIn(credentials)
      .then(user => {
        console.log(user)
        setLoggedIn(true);
        setUser(user);
        navigate('/');
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
      <Route path="/registration" element={loggedIn ? <Navigate to="/" /> : <RegistrationForm login={doLogIn}/>}/>
    </Routes>
  );
}

export default App;