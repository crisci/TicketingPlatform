import formatDateTime from '../../../utils/DateTimeFormatter';
import './custommessages.css'
import { Col, Container, ListGroup } from "react-bootstrap";

function MessageItem(props) {

    return (
        <Container>
            {props.message.expert != null
                ?
                <>
                    <Container className="d-flex justify-content-end">
                        <ListGroup.Item active key={props.message.id} as='li' className="d-flex mb-3 py-3 w-50">
                            <Col style={{ padding: 0 }}>
                                <Container className='text-start' style={{ wordBreak: 'break-all' }}>
                                    {props.message.body}
                                </Container>
                                <Container className='text-end' style={{fontSize:"0.85rem"}}>
                                    {formatDateTime(props.message.date)}
                                </Container>
                            </Col>
                        </ListGroup.Item>
                    </Container>

                </>
                :
                <>
                    <span className="d-flex justify-content-left">{`${props.user.firstName} ${props.user.lastName}`}&nbsp;</span>
                    <Container className="d-flex justify-content-start">
                        <ListGroup.Item key={props.message.id} as='li' className="d-flex mb-3 py-3 w-50">
                            <Col style={{ padding: 0 }}>
                                <Container className='text-start' style={{ wordBreak: 'break-all' }}>
                                    {props.message.body}
                                </Container>
                                <Container className='text-end' style={{fontSize:"0.85rem"}}>
                                    {formatDateTime(props.message.date)}
                                </Container>
                            </Col>
                        </ListGroup.Item>
                    </Container>

                </>
            }
        </Container>
    )
}

export default MessageItem;