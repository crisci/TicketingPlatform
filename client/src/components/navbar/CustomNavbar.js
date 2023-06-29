
import "./customnavbar.css"
import { Navbar, Container, Nav, Button } from "react-bootstrap"
import { useNavigate } from "react-router-dom";

export function CustomNavbar(props) {
    
    const navigate = useNavigate();

    return (

        <Navbar bg="dark" variant="dark" className="navbar navbar-dark bg-primary fixed-top">
            <Container fluid style={{ paddingLeft: "1.2rem", paddingRight: "1.2rem" }}>
            <Navbar.Brand>Tickets</Navbar.Brand>
            {props.user.role === "Client" ? <Nav className="me-auto">
                <Nav.Link onClick={() => navigate("/")}>Your Tickets</Nav.Link>
                <Nav.Link onClick={() => navigate("/yourdevices")}>Your Products</Nav.Link>
            </Nav> : null}
            </Container>
            <Container className="justify-content-end">
                <p style={{color:"white", margin:0, paddingRight:"1rem", fontSize:"1rem"}}>{`${props.user.firstName} ${props.user.lastName}`}</p>
                <Button variant="danger" className="logout-button" onClick={props.handleLogout}>Log out</Button>
            </Container>
        </Navbar>
    )
}