import { useState } from "react";
import { Badge, Container, ListGroup, OverlayTrigger } from "react-bootstrap";
import { BsCheckCircleFill, BsFillInfoCircleFill, BsXCircleFill } from "react-icons/bs";
import { IoMdRefreshCircle } from "react-icons/io";
import TicketModal from "./TicketModal";
import mapStatus from "../../../utils/MapStatus";


function TicketItem(props) {

    const [showModal, setShowModal] = useState(false);

    const handleShowModal = () => setShowModal(oldValue => !oldValue);


    return (

        <ListGroup.Item as='li' className="d-flex justify-content-beetween mb-3 py-3">
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
                {props.ticket.status !== "CLOSED"
                    ? <div style={{ display: 'inline-block', width: '30px', height: '30px' }}>
                        <OverlayTrigger placement="top" overlay={props.tooltip("Close ticket")}>
                            <div style={{ cursor: 'pointer' }} onClick={() => props.handleX(props.ticket.id)}>
                                <BsXCircleFill color="red" size="20px" />
                            </div>
                        </OverlayTrigger>
                    </div>
                    : <div style={{ display: 'inline-block', width: '30px', height: '30px' }}>
                        <OverlayTrigger placement="top" overlay={props.tooltip("Reopen ticket")}>
                            <div style={{ cursor: 'pointer' }} onClick={() => props.handleR(props.ticket.id)}>
                                <IoMdRefreshCircle color="#0d6efd" size="25px" />
                            </div>
                        </OverlayTrigger>
                    </div>}
                {
                    props.ticket.status !== "CLOSED"
                        ? <div style={{ display: 'inline-block', width: '30px', height: '30px' }}>
                            <OverlayTrigger placement="top" overlay={props.tooltip("Resolve ticket")}>
                                <div style={{ cursor: 'pointer' }} onClick={() => props.handleV(props.ticket.id)}>
                                    <BsCheckCircleFill color="green" size="20px" />
                                </div>
                            </OverlayTrigger>
                        </div>
                        : null
                }
            </Container>
            <TicketModal show={showModal} onHide={handleShowModal} ticket={props.ticket} />
        </ListGroup.Item>
    )

}

export default TicketItem;