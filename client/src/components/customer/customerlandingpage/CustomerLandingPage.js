import { Button, Col, Container, Row } from "react-bootstrap";
import "./customerlandingpage.css"
import { Outlet, useNavigate } from "react-router-dom";


function CustomerLandingPage(props) {

    const navigate = useNavigate()

    function handleYourDevices() {
        navigate("/yourdevices")
    }

    function handleHome() {
        navigate("/")
    }

    function handleAskQuestion() {
        navigate("/askquestion")
    }

    return (
        <Container fluid style={{ paddingTop: "4rem", height: "100vh", justifyContent:"center", alignItems:"center", textAlign:"center" }}>
            <Outlet />
        </Container>
    )
}

export default CustomerLandingPage;