import { Container, Form, Row, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import TicketList from "./TicketList";
import { useState } from "react";

function ExpertTickets(props) {

    const navigate = useNavigate();
    const [nameFilter, setNameFilter] = useState("")

    return (
        <Container className="mt-3">
            <Container className="d-flex justify-content-center align-items-center">
                <h1 className="m-0">Expert Tickets</h1>
            </Container>
            <Form className="d-flex justify-content-center pt-3">
                <Form.Group className="w-50">
                    <Form.Control type="text" placeholder="Search..." value={nameFilter} onChange={event => setNameFilter(event.target.value)}/>
                </Form.Group>
            </Form>
            <Row className="d-flex justify-content-center mt-4">
                {!props.loadingTickets ? <TicketList tickets={props.tickets.filter(t => ["IN_PROGRESS", "CLOSED", "RESOLVED"].includes(t.status))} messages={props.messages} loadingMessages={props.loadingMessages} nameFilter={nameFilter} getMessages={props.getMessages} stopTicket={props.stopTicket}/> : <Spinner  variant="primary"/>}
            </Row>
        </Container>
    )
}

export default ExpertTickets;
