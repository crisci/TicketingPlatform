import { Container, ListGroup, Tooltip } from "react-bootstrap";
import TicketItem from "./TicketItem";

function TicketList(props) {

    function handleS(ticketId) {
        props.stopTicket(ticketId, props.user)
    }

    function handleM(ticketId) {
        props.getMessages(ticketId)
    }

    const tooltip = (message) => {
        return <Tooltip id="tooltip">{message}</Tooltip>
    }


    return (
        <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>Status</Container>
                <Container>Title</Container>
                <Container>Actions</Container>
            </ListGroup.Item>
            {
                props.tickets.filter(a => {return a.title.startsWith(props.nameFilter)}).sort((a,b) => a.dateTime < b.dateTime).map(ticket => <TicketItem key={ticket.id} messages={props.messages} loadingMessages={props.loadingMessages} tooltip={tooltip} handleS={handleS} handleM={handleM} ticket={ticket}/>)
            }
        </ListGroup>
    )
}

export default TicketList;