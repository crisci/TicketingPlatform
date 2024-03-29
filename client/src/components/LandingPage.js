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
            case "Manager":
                return <CustomerLandingPage user={props.user}/>
            default:
                return null
    }}
    

    return(
        <>
        <CustomNavbar handleLogout={props.handleLogout} user={props.user}/>
        { renderLandingPage(props.user.role) }
        </>
    )
}


export default LandingPage;
