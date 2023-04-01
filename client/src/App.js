import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import LandingPage from './LandingPage';


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



  return (
    <Routes>
      <Route path="/" element={<LandingPage/>}></Route>
    </Routes>
  );
}

export default App;
