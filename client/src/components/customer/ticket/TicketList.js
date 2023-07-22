import { Container, ListGroup, Tooltip } from "react-bootstrap";
import TicketItem from "./TicketItem";

function TicketList(props) {

    function handleX(ticketId) {
        props.closeTicket(ticketId)
    }

    function handleV(ticketId) {
        props.resolveTicket(ticketId)
    }

    function handleR(ticketId) {
        props.reopenTicket(ticketId)
    }

    const tooltip = (message) => {
        return <Tooltip id="tooltip">{message}</Tooltip>
    }
    
    const numberOfFilteredTickets = props.tickets.filter(a => {return a.title.toLowerCase().startsWith(props.nameFilter.toLowerCase())}).length

    return (
        <>
            {numberOfFilteredTickets === 0
                ? <h3>0 tickets found.</h3>
                : <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>Status</Container>
                <Container>Title</Container>
                <Container>Actions</Container>
            </ListGroup.Item>
            {  
                props.tickets.filter(a => {return a.title.toLowerCase().startsWith(props.nameFilter.toLowerCase())}).sort((a,b) => a.dateTime < b.dateTime).map(ticket => <TicketItem key={ticket.id} tooltip={tooltip} handleR={handleR} handleV={handleV} handleX={handleX} ticket={ticket}/>)
            }
        </ListGroup>}
        </>
    )
}

export default TicketList;