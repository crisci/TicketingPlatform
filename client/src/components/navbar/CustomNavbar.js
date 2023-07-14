
import "./customnavbar.css"
import { Navbar, Container, Nav, Button, Card, Spinner } from "react-bootstrap"
import { BsCheckCircleFill, BsXCircleFill } from "react-icons/bs";
import { IoMdRefreshCircle } from "react-icons/io";
import { useNavigate } from "react-router-dom";

export function CustomNavbar(props) {
    
    const navigate = useNavigate();

    return (
        <Navbar bg="dark" variant="dark" className="navbar navbar-dark bg-primary fixed-top">
            <Container fluid style={{ paddingLeft: "1.2rem", paddingRight: "1.2rem" }}>
            <Navbar.Brand>Tickets</Navbar.Brand>
            {props.user.role === "Client" ? <Nav className="me-auto">
                <Nav.Link onClick={() => navigate("/")}>Your Tickets</Nav.Link>
                <Nav.Link onClick={() => navigate("/yourproducts")}>Your Products</Nav.Link>
            </Nav> : null}
            {props.user.role === "Expert" ? <Nav className="me-auto">
                <Nav.Link onClick={() => navigate("/")}>Your Tickets</Nav.Link>
            </Nav> : null}
            </Container>
            <Container className="justify-content-end">
                {props.user.role === "Expert" ?
                    !props.loadingTickets ?
                    <Card className="p-1">
                        <span>Ticket stats: {props.tickets.filter(t => t.status === "IN_PROGRESS").length} <IoMdRefreshCircle color="#0d6efd" size="25px" /> 
                        {props.tickets.filter(t => t.status === "RESOLVED").length} <BsCheckCircleFill color="green" size="20px" /> 
                        {props.tickets.filter(t => t.status === "CLOSED").length} <BsXCircleFill color="red" size="20px" /></span>
                    </Card> : <Spinner  variant="primary"/> : <></>
                }
                <p style={{color:"white", margin:0, paddingRight:"1rem", fontSize:"1rem"}}>{`${props.user.firstName} ${props.user.lastName}`}</p>
                <Button variant="danger" className="logout-button" onClick={props.handleLogout}>Log out</Button>
            </Container>
        </Navbar>
    )
}