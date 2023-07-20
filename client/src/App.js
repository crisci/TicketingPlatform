import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import LandingPage from './components/LandingPage';
import LoginForm from './components/login/LoginForm';
import { useEffect, useState } from 'react';
import API from './API';
import RegistrationForm from './components/registration/SignUpForm';
import jwt from 'jwt-decode'
import Notification from './utils/Notifications';
import YourTickets from './components/customer/ticket/YourTickets';
import OpenTicket from './components/customer/ticket/OpenTicket';
import YourProducts from './components/customer/product/YourProducts';
import NotFoundPage from './components/404notfound/NotFoundPage';
import MessageConversation from './components/expert/chat/MessageConversation';

function App() {
  return (
    <>
      <Router>
        <ToastContainer />
        <MainApp />
      </Router>
    </>
  );
}

function MainApp(props) {

  const navigate = useNavigate();

  const [user, setUser] = useState({});
  const [loggedIn, setLoggedIn] = useState(false)

  const [products, setProducts] = useState([])
  const [tickets, setTickets] = useState([])
  const [messages, setMessages] = useState([])


  const [loadingTickets, setLoadingTickets] = useState(true)
  const [loadingMessages, setLoadingMessages] = useState(true)



  useEffect(() => {
    const loggedInUser = localStorage.getItem("jwt");

    if (loggedInUser) {
      //updated asynchronously
      setUser(jwtToUser(jwt(loggedInUser)))
      setLoggedIn(true);
      getProducts()
      getTickets()
    }
    // eslint-disable-next-line
  }, [])

  const jwtToUser = (jwt) => {
    return {
      email: jwt.email,
      firstName: jwt.given_name,
      lastName: jwt.family_name,
      role: jwt.resource_access["authN"].roles[0]
    }
  }

  const doLogIn = async (credentials) => {
    return API.logIn(credentials)
      .then(res => {
        setUser(jwtToUser(jwt(res.access_token)));
        setLoggedIn(true);
        getProducts()
        getTickets()
        navigate('/')
      }).catch(err => {console.log("AAAAAAAAAA")})
  }

  const doSignup = async (credentials) => {
    return API.signup(credentials)
      .then(() => {
        doLogIn({ username: credentials.username, password: credentials.password })
      }).catch(err => {console.log("AAAAAAAAAA")})
  }

  const handleLogout = () => {
    setLoggedIn(false)
    setUser({})
    localStorage.removeItem("jwt")
    navigate('/')
  }

  const addProduct = (ean) => {
    return API.addProduct(user, ean).then(res => {
      Notification.showSuccess("Product added correctly")
      getProducts()
    }).catch(err => {
      Notification.showError(err.detail)
    })
  }

  const removeProduct = (ean) => {
     API.removeProduct(user, ean).then(res => {
      Notification.showSuccess("Product removed correctly")
      getProducts()
    })
  }

  const getProducts = () => {
    return API.getProducts(user).then(res => {
      setProducts(res)
    }).catch(err => {Notification.showError(err.detail)})
  }

  const getTickets = () => {
    setLoadingTickets(true)
    return API.getTickets().then(res => {
      setTickets(res)
      setLoadingTickets(false)
    }).catch(err => {Notification.showError(err.detail); setLoadingTickets(false)})
  }

  const openTicket = (ticket) => {
    return API.openTicket(ticket).then(_ => {
      Notification.showSuccess("Ticket added correctly")
      getTickets()
    }).catch(err => {
      Notification.showError(err.detail)
    })
  }

  const closeTicket = (ticketId) => {
    return API.closeTicket(ticketId).then(_ => {
      Notification.showSuccess("Ticket closed correctly")
      getTickets()
    }).catch(err => {
      Notification.showError(err.detail)
    })
  }

  const resolveTicket = (ticketId) => {
    return API.resolveTicket(ticketId).then(_ => {
      Notification.showSuccess("Ticket resolved correctly")
      getTickets()
    }).catch(err => {
      Notification.showError(err.detail)
    })
  }

  const reopenTicket = (ticketId) => {
    return API.reopenTicket(ticketId).then(_ => {
      Notification.showSuccess("Ticket reopened correctly")
      getTickets()
    }).catch(err => {
      Notification.showError(err.detail)
    })
  }


  const getMessages = (ticketId) => {
    return API.getMessages(ticketId).then(res => {
      setMessages(res)
      setLoadingMessages(false)
    }).catch(err => {Notification.showError(err.detail); setLoadingMessages(false)})
  }

  const addExpertMessage = (ticketId, message) => {
    return API.addExpertMessage(ticketId, message).then(res => {
      Notification.showSuccess("Message added correctly")
      getMessages(ticketId)
    }).catch(err => {
      Notification.showError(err.detail)
    })
  }

  const addClientMessage = (ticketId, message) => {
    return API.addClientMessage(ticketId, message).then(res => {
      Notification.showSuccess("Message added correctly")
      getMessages(ticketId)
    }).catch(err => {
      Notification.showError(err.detail)
    })
  }






  return (
    <Routes>
      <Route path="/" element={
        !loggedIn
          ? <Navigate to="/login" />
          : <LandingPage user={user} handleLogout={handleLogout} />
      }>
        <Route path="/" element={<YourTickets tickets={tickets} loadingTickets={loadingTickets} closeTicket={closeTicket} resolveTicket={resolveTicket} reopenTicket={reopenTicket}/>}/>
        <Route path="/yourproducts" element={<YourProducts products={products} addProduct={addProduct} removeProduct={removeProduct}/>}/>
        <Route path="/openticket" element={<OpenTicket products={products} openTicket={openTicket}/>}/>
        <Route path="/chat/:id" element={<MessageConversation user={user} tickets={tickets} getMessages={getMessages} messages={messages} loadingMessages={loadingMessages} addMessage={user.role === "Client" ? addClientMessage : addExpertMessage} />} />
      </Route>
      <Route path="/login" element={loggedIn ? <Navigate to="/" /> : <LoginForm login={doLogIn} />} />
      <Route path="/registration" element={loggedIn ? <Navigate to="/" /> : <RegistrationForm signup={doSignup} />} />
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
}

export default App;