import { Container, ListGroup, Tooltip } from "react-bootstrap";
import TicketItem from "./TicketItem";

function TicketList(props) {

    function handleS(ticketId) {
        props.stopTicket(ticketId)
    }

    const tooltip = (message) => {
        return <Tooltip id="tooltip">{message}</Tooltip>
    }

    const numberOfFilteredTickets = props.tickets.filter(a => {return a.title.toLowerCase().startsWith(props.nameFilter.toLowerCase())}).length

    return (
        <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>Status</Container>
                <Container>Title</Container>
                <Container>Actions</Container>
            </ListGroup.Item>
            {   numberOfFilteredTickets === 0 
                    ?  <h3 className="mt-4">0 {props.selectedStatus.replace("_", " ").toLowerCase()} tickets found.</h3> 
                    : props.tickets.filter(a => {return a.title.startsWith(props.nameFilter)}).sort((a,b) => a.dateTime < b.dateTime).map(ticket => <TicketItem key={ticket.id} tooltip={tooltip} handleS={handleS} ticket={ticket}/>)
            }
        </ListGroup>
    )
}

export default TicketList;
