import { Button, Container, Row, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import TicketList from "./TicketList";

function YourTickets(props) {

    const navigate = useNavigate();

    return (
        <Container className="mt-3">
            <h1>Your Tickets</h1>
            <Button onClick={() => {navigate("/openticket")}}>{"Open Ticket"}</Button>
            <Row className="d-flex justify-content-center mt-4">
                {!props.loadingTickets ? <TicketList tickets={props.tickets} /> : <Spinner  variant="primary"/>}
            </Row>
        </Container>
    )
}

export default YourTickets;