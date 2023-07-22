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
import AdminMainPage from './components/admin/AdminMainPage';
import MessageConversation from './components/expert/chat/MessageConversation';
import TicketDetails from './components/admin/TicketDetails';
import ExpertRegistration from './components/admin/ExpertRegistration';

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
  const [loadingExpertRegistration, setLoadingExpertRegistration] = useState(false)



  useEffect(() => {
    const loggedInUser = localStorage.getItem("jwt");

    if (loggedInUser && user.role === "Client") {
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
        if (jwtToUser(jwt(res.access_token)).role !== "Manager") {
          getProducts()
          getTickets()
        }
        navigate('/')
      }).catch(err => { Notification.showError(err.detail) })
  }

  const doSignup = async (credentials) => {
    return API.signup(credentials)
      .then(() => {
        doLogIn({ username: credentials.username, password: credentials.password })
      }).catch(err => { Notification.showError(err.detail) })
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
    }).catch(err => { Notification.showError(err.detail) })
  }

  const getTickets = () => {
    setLoadingTickets(true)
    return API.getTickets().then(res => {
      setTickets(res)
      setLoadingTickets(false)
    }).catch(err => { Notification.showError(err.detail); setLoadingTickets(false) })
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
    setLoadingMessages(true)
    return API.getMessages(ticketId).then(res => {
      setMessages(res)
      setLoadingMessages(false)
    }).catch(err => { Notification.showError(err.detail); setLoadingMessages(false) })
  }

  const addExpertMessage = (ticketId, message) => {
    return API.addExpertMessage(ticketId, message).then(res => {
      Notification.showSuccess("Message added correctly")
      getMessages(ticketId)
    }).catch(err => {
      Notification.showError(err.detail)
    })
  }

  const addClientMessage = (ticketId, message, listOfAttachments) => {
    return API.addClientMessage(ticketId, message, listOfAttachments).then(res => {
      Notification.showSuccess("Message added correctly")
      getMessages(ticketId)
    }).catch(err => {
      Notification.showError(err.detail)
    })
  }

  const handleCreateExpert = (expert) => {
    setLoadingExpertRegistration(true)
    return API.expertRegistration(expert).then(res => {
      navigate("/")
      Notification.showSuccess("Expert created correctly")
      setLoadingExpertRegistration(false)
    }).catch(err => {
      setLoadingExpertRegistration(false)
      Notification.showError(err.detail)
    })
  }

  const handleCloseChat = () => {
    setMessages([])
    navigate('/')
  }


  return (
    <Routes>
      <Route path="/" element={
        !loggedIn
          ? <Navigate to="/login" />
          : <LandingPage user={user} handleLogout={handleLogout} />
      }>
        {user.role === "Client"
          ? <><Route path="/" element={<YourTickets tickets={tickets} loadingTickets={loadingTickets} closeTicket={closeTicket} resolveTicket={resolveTicket} reopenTicket={reopenTicket} />} />
            <Route path="/yourproducts" element={<YourProducts products={products} addProduct={addProduct} removeProduct={removeProduct} />} />
            <Route path="/openticket" element={<OpenTicket products={products} openTicket={openTicket} />} />
            <Route path="/chat/:id" element={<MessageConversation user={user} tickets={tickets} getMessages={getMessages} messages={messages} loadingMessages={loadingMessages} handleCloseChat={handleCloseChat} addMessage={user.role === "Client" ? addClientMessage : addExpertMessage} />} />
            <Route path="/:ticketId/details" element={<TicketDetails />} /></>
          : null
        }
        {
          user.role === "Manager"
            ? <>
              <Route path="/" element={<AdminMainPage handleLogout={handleLogout}/>} />
              <Route path="/expertRegistration" element={<ExpertRegistration handleCreateExpert={handleCreateExpert} handleLogout={handleLogout} loadingExpertRegistration={loadingExpertRegistration}/>} />
              <Route path="/:ticketId/details" element={<TicketDetails />} />
            </>
            : null
        }
      </Route>
      <Route path="/login" element={loggedIn ? <Navigate to="/" /> : <LoginForm login={doLogIn} />} />
      <Route path="/registration" element={loggedIn ? <Navigate to="/" /> : <RegistrationForm signup={doSignup} />} />
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
}

export default App;