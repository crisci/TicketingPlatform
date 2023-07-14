import { Button, Container, Form, Row, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import TicketList from "./TicketList";
import { useState } from "react";

function YourTickets(props) {

    const navigate = useNavigate();
    const [nameFilter, setNameFilter] = useState("")

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
                {!props.loadingTickets ? <TicketList tickets={props.tickets} messages={props.messages} loadingMessages={props.loadingMessages} getMessages={props.getMessages} nameFilter={nameFilter} closeTicket={props.closeTicket} resolveTicket={props.resolveTicket} reopenTicket={props.reopenTicket}/> : <Spinner  variant="primary"/>}
            </Row>
        </Container>
    )
}

export default YourTickets;