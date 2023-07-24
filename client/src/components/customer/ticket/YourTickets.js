import { Button, Container, Form, Row, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import TicketList from "./TicketList";
import { useState, useEffect } from "react";

function YourTickets(props) {

    const navigate = useNavigate();
    const [nameFilter, setNameFilter] = useState("")
    const [spin, setSpin] = useState(true)

    const REFRESH = 5000;

    useEffect(() => {
        const interval = setInterval(() => {
            console.log("tickets")
            props.getTickets(props.user)
        }, REFRESH);

        return() => clearTimeout(interval);// This represents the unmount function, in which you need to clear your interval to prevent memory leaks.
        // eslint-disable-next-line
    }, []);

    useEffect(() => { //Use spin state instead of loadingMessages to avoid Spinner animation at every poll
        if(props.loadingMessages === false && spin === true){
            setSpin(false);
        }
        // eslint-disable-next-line
    }, [props.loadingMessages]);

    return (
        <Container className="mt-3">
            <Container className="d-flex justify-content-center align-items-center">
                <h1 className="m-0">Your Tickets</h1>
                <Button style={{fontWeight:"700"}} className="mx-3 py-2" onClick={() => {navigate("/openticket")}}>{"Open Ticket"}</Button>
            </Container>
            <Form className="d-flex justify-content-center pt-3">
                <Form.Group className="w-50">
                    <Form.Control type="text" placeholder="Search..." value={nameFilter} onChange={event => setNameFilter(event.target.value)}/>
                </Form.Group>
            </Form>
            <Row className="d-flex justify-content-center mt-4">
                {spin
                    ? props.tickets.length !== 0 
                        ? <TicketList tickets={props.tickets} nameFilter={nameFilter} closeTicket={props.closeTicket} resolveTicket={props.resolveTicket} reopenTicket={props.reopenTicket}/> 
                        : <h2>You don't have opened a ticket yet!</h2>
                    : <Spinner  variant="primary"/>}
            </Row>
        </Container>
    )
}

export default YourTickets;