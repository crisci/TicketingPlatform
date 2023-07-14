import 'react-toastify/dist/ReactToastify.css';
import './list.css'
import { CustomNavbar } from "./navbar/CustomNavbar";
import CustomerLandingPage from "./customer/customerlandingpage/CustomerLandingPage";
import ExpertLandingPage from './expert/expertlandingpage/ExpertLandingPage';


function LandingPage(props) {


    function renderLandingPage(role) {
        switch(role) {
            case "Client":
                return <CustomerLandingPage user={props.user}/>
            case "Expert":
                return <ExpertLandingPage user={props.user}/>
            default:
                return null
    }}
    
    switch(props.user.role){
        case "Client":
            return(
                <>
                <CustomNavbar handleLogout={props.handleLogout} user={props.user}/>
                { renderLandingPage(props.user.role) }
                </>
            )
        case "Expert":
            return(
                <>
                <CustomNavbar handleLogout={props.handleLogout} user={props.user} tickets={props.tickets} loadingTickets={props.loadingTickets}/>
                { renderLandingPage(props.user.role) }
                </>
            )
        default:
            return null
    }
    
}


export default LandingPage;