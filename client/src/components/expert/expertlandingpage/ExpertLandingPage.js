import { Container } from "react-bootstrap";
import "./expertlandingpage.css"
import { Outlet } from "react-router-dom";


function ExpertLandingPage(props) {



    return (
        <Container fluid style={{ paddingTop: "4rem", height: "100vh", justifyContent:"center", alignItems:"center", textAlign:"center" }}>
            <Outlet />
        </Container>
    )
}

export default ExpertLandingPage;