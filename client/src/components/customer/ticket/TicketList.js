import { Badge, Container, ListGroup, OverlayTrigger, Tooltip } from "react-bootstrap";
import { BsXCircleFill, BsFillInfoCircleFill, BsCheckCircleFill } from "react-icons/bs";

function TicketList(props) {

    function handleX(ean) {
        props.removeProduct(ean)
    }

    const tooltip = (message) => {
        return <Tooltip id="tooltip">{message}</Tooltip>
    }

    const mapStatus = (status) => {
        switch (status) {
            case "OPEN":
                return "primary"
            case "IN_PROGRESS":
                return "warning"
            case "CLOSED":
                return "danger"
            case "RESOLVED":
                return "success"
            case "REOPENED":
                return "secondary"
            default:
                return "primary"
        }
    }

    return (
        <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="title" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>Status</Container>
                <Container>Title</Container>
                <Container>Actions</Container>
            </ListGroup.Item>
            {
                props.tickets.map(ticket =>
                    <ListGroup.Item key={ticket.id} as='li' className="d-flex justify-content-beetween mb-3 py-3">
                        <Container><Badge pill text={ticket.status === "IN_PROGRESS" ? "dark" : null} bg={mapStatus(ticket.status)}>{ticket.status}</Badge></Container>
                        <Container>{ticket.title}</Container>
                        <Container flex>
                            <div style={{ display: 'inline-block', width: '30px', height: '30px'}}>
                                <OverlayTrigger placement="top" overlay={tooltip("Details")}>
                                    <div style={{ cursor: 'pointer' }} onClick={() => handleX(ticket.id)}>
                                        <BsFillInfoCircleFill color="grey" size="20px" />
                                    </div>
                                </OverlayTrigger>
                            </div>
                            <div style={{ display: 'inline-block',  width: '30px', height: '30px'}}>
                                <OverlayTrigger placement="top" overlay={tooltip("Close ticket")}>
                                    <div style={{ cursor: 'pointer' }} onClick={() => handleX(ticket.id)}>
                                        <BsXCircleFill color="red" size="20px" />
                                    </div>
                                </OverlayTrigger>
                            </div>
                            <div style={{ display: 'inline-block',  width: '30px', height: '30px'}}>
                                <OverlayTrigger placement="top" overlay={tooltip("Resolve ticket")}>
                                    <div style={{ cursor: 'pointer' }} onClick={() => handleX(ticket.id)}>
                                        <BsCheckCircleFill color="green" size="20px" />
                                    </div>
                                </OverlayTrigger>
                            </div>
                        </Container>
                    </ListGroup.Item>)
            }
        </ListGroup>
    )
}

export default TicketList;