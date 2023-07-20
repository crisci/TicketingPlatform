import { useState } from "react";
import { Badge, Container, ListGroup, OverlayTrigger } from "react-bootstrap";
import { BsFillInfoCircleFill, BsXCircleFill, BsSignal } from "react-icons/bs";
import { useNavigate } from "react-router-dom";
import TicketModal from "./TicketModal";
import mapStatus from "../../../utils/MapStatus";


function TicketItem(props) {

    const navigate = useNavigate();
    const [showModal, setShowModal] = useState(false);
    const handleShowModal = () => setShowModal(oldValue => !oldValue);


    return (

        <ListGroup.Item key={props.ticket.id} as='li' className="d-flex justify-content-beetween mb-3 py-3">
            <Container><Badge pill text={props.ticket.status === "IN_PROGRESS" ? "dark" : null} bg={mapStatus(props.ticket.status)}>{props.ticket.status}</Badge></Container>
            <Container>{props.ticket.title}</Container>
            <Container>
                <div style={{ display: 'inline-block', width: '30px', height: '30px' }}>
                    <OverlayTrigger placement="top" overlay={props.tooltip("Details")}>
                        <div style={{ cursor: 'pointer' }} onClick={() => { handleShowModal() }}>
                            <BsFillInfoCircleFill color="grey" size="20px" />
                        </div>
                    </OverlayTrigger>
                </div>
                {props.ticket.status === "IN_PROGRESS"
                    ? <div style={{ display: 'inline-block', width: '30px', height: '30px' }}>
                        <OverlayTrigger placement="top" overlay={props.tooltip("Stop ticket")}>
                            <div style={{ cursor: 'pointer' }} onClick={() => props.handleS(props.ticket.id)}>
                                <BsXCircleFill color="red" size="20px" />
                            </div>
                        </OverlayTrigger>
                    </div>
                    : null
                }
                <div style={{ display: 'inline-block', width: '30px', height: '30px' }}>
                    <OverlayTrigger placement="top" overlay={props.tooltip("Messages")}>
                        <div style={{ cursor: 'pointer' }} onClick={() => {props.handleM(props.ticket.id); navigate("/messages", { state: { ticket: props.ticket } })}}>
                            <BsSignal color="#0d6efd" size="20px" />
                        </div>
                    </OverlayTrigger>
                </div>
            </Container>
            <TicketModal show={showModal} onHide={handleShowModal} ticket={props.ticket} />
        </ListGroup.Item>
    )

}

export default TicketItem;