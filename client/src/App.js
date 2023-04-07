import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import { DefaultLayout, LandingPage } from './LandingPage';


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/*" element={<MainApp />} />
      </Routes>
    </BrowserRouter>
  );
}

function MainApp(props) {

  return (
    <Routes>
      <Route path="/" element={<DefaultLayout/>}>
        <Route index element={<LandingPage/>}></Route>
      </Route>
    </Routes>
  );
  
}

export default App;
