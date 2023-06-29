import { Container } from "react-bootstrap";
import TicketForm from "./TicketForm";


function OpenTicket(props) {
    return (
        <Container className="mt-3">
            <h1>Open Ticket</h1>
            <TicketForm products={props.products} openTicket={props.openTicket}/>
        </Container>

    )
}

export default OpenTicket;