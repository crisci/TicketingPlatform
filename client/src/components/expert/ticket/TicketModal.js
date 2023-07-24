import { Badge, Col, Container, Row } from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal';
import formatDateTime from '../../../utils/DateTimeFormatter';
import mapStatus from '../../../utils/MapStatus';

function TicketModal(props) {

    return (
        <Modal
            {...props}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Ticket Details
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Container>
                    <Row className='p-2'>
                        <Col style={{ overflowWrap: 'break-word', padding: 0}}>
                            <h4>{props.ticket.title}</h4>
                            <p>{props.ticket.description}</p>
                        </Col>
                        <Col>
                            <Row className="align-items-center mb-3">
                                <Col style={{ display: "contents" }}>
                                    <h5 className='m-0'>Opened: </h5>
                                </Col>
                                <Col>
                                    <p className='m-0'>{formatDateTime(props.ticket.openDate)}</p>
                                </Col>
                            </Row>
                            <Row className="align-items-center mb-3">
                                <Col style={{ display: "contents" }}>
                                    <h5 className='m-0'>Status: </h5>
                                </Col>
                                <Col style={{ display: "contents" }}>
                                    <Badge className='d-flex justify-content-center mx-2' pill text={props.ticket.status === "IN_PROGRESS" ? "dark" : null} bg={mapStatus(props.ticket.status)}>
                                        {props.ticket.status}
                                    </Badge>
                                </Col>
                            </Row>
                            <Row>
                                <Col style={{ display: "contents" }}>
                                    <h5 className='m-0'>Product: </h5>
                                </Col>
                                <Col>
                                    <p className='m-0'>{props.ticket.product.name}</p>
                                </Col>
                            </Row>
                        </Col>
                    </Row>
                </Container>
            </Modal.Body>
        </Modal>
    );
}

export default TicketModal;
