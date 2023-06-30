import { Badge, Container, ListGroup, OverlayTrigger, Tooltip } from "react-bootstrap";
import { BsXCircleFill, BsFillInfoCircleFill, BsCheckCircleFill } from "react-icons/bs";
import { IoMdRefreshCircle } from "react-icons/io";
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


    return (
        <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="title" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>Status</Container>
                <Container>Title</Container>
                <Container>Actions</Container>
            </ListGroup.Item>
            {
                props.tickets.map(ticket => <TicketItem tooltip={tooltip} handleR={handleR} handleV={handleV} handleX={handleX} ticket={ticket}/>)
            }
        </ListGroup>
    )
}

export default TicketList;