
import "./customnavbar.css"
import { Navbar, Container, Nav, Button, Spinner, Card } from "react-bootstrap"
import { BsCheckCircleFill, BsFillPlayCircleFill, BsXCircleFill } from "react-icons/bs";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

export function CustomNavbar(props) {

    const navigate = useNavigate();
    const [spin, setSpin] = useState(true)

    const REFRESH = 5000;

    useEffect(() => {
        if(props.user.role === "Expert"){
            const interval = setInterval(() => {
                console.log("tickets")
                props.getTickets(props.user)
            }, REFRESH);

            return() => clearTimeout(interval);// This represents the unmount function, in which you need to clear your interval to prevent memory leaks.
        }
        // eslint-disable-next-line
    }, []);

    useEffect(() => { //Use spin state instead of loadingMessages to avoid Spinner animation at every poll
        if(props.loadingMessages === false && spin === true){
            setSpin(false);
        }
        // eslint-disable-next-line
    }, [props.loadingMessages]);

    return (

        <Navbar bg="dark" variant="dark" className="navbar navbar-dark bg-primary fixed-top">
            <Container fluid style={{ paddingLeft: "1.2rem", paddingRight: "1.2rem" }}>
                <Navbar.Brand style={{ cursor: "pointer" }} onClick={() => navigate("/")}>Tickets</Navbar.Brand>
                {props.user.role === "Client" ? <Nav className="me-auto">
                    <Nav.Link onClick={() => navigate("/")}>Your Tickets</Nav.Link>
                    <Nav.Link onClick={() => navigate("/yourproducts")}>Your Products</Nav.Link>
                </Nav> : null}
                {props.user.role === "Manager" ? <Nav className="me-auto">
                    <Nav.Link onClick={() => navigate("/expertRegistration")}>Expert registration</Nav.Link>
                    <Nav.Link onClick={() => navigate("/products")}>Products</Nav.Link>
                    <Nav.Link onClick={() => navigate("/experts")}>Experts</Nav.Link>
                </Nav>
                    : null}
                {props.user.role === "Expert" ? <Nav className="me-auto">
                    <Nav.Link onClick={() => navigate("/")}>Your Tickets</Nav.Link>
                </Nav> : null}
                {props.user.role === "Expert" ?
                    spin ?
                        <Card className="p-1">
                            <span>Ticket stats: {props.tickets.filter(t => t.status === "IN_PROGRESS").length} <BsFillPlayCircleFill color="#ffc107" size="20px" className="me-2" />
                                {props.tickets.filter(t => t.status === "RESOLVED").length} <BsCheckCircleFill color="green" size="20px" className="me-2"/>
                                {props.tickets.filter(t => t.status === "CLOSED").length} <BsXCircleFill color="red" size="20px"/></span>
                        </Card> : <Spinner variant="primary" /> : <></>
                }
            </Container>
            <Container className="justify-content-end">
                <p style={{ color: "white", margin: 0, paddingRight: "1rem", fontSize: "1rem" }}>{`${props.user.firstName} ${props.user.lastName}`}</p>
                <Button variant="danger" className="logout-button" onClick={props.handleLogout}>Log out</Button>
            </Container>
        </Navbar>
    )
}
