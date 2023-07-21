import { Card, ListGroup, Container, Form, Button, InputGroup, Spinner, Row, Alert, Badge } from "react-bootstrap";
import { useEffect, useState } from "react";
import MessageItem from "./MessageItem";
import dayjs from "dayjs";
import { useNavigate, useParams } from "react-router-dom";
import mapStatus from "../../../utils/MapStatus";

function MessageConversation(props) {

    const params = useParams()

    const reverseMessages = [...props.messages].sort((a, b) => { return new Date(a.date).getTime() - new Date(b.date).getTime() }).reverse()
    const [ticket, setTicket] = useState({})
    const [send, setSend] = useState("")

    useEffect(() => {
        setTicket(props.tickets.find(t => t.id.toString() === params.id))
        props.getMessages(params.id)
        // eslint-disable-next-line
    }, [])


    const handleSend = () => {
        props.addMessage(params.id, send)
        setSend("")
    }


    return (
        <>
            {ticket === undefined
                ? <Spinner variant="primary" />
                : <Container>
                    <h2 className="text-center">{ticket.title}</h2>
                    <p className="text-center">{ticket.description}</p>
                    <Row className="d-flex justify-content-center"><Button className="mb-3 w-25" variant="danger" onClick={() => props.handleCloseChat()}>Close chat</Button></Row>
                    {props.loadingMessages === true
                        ? <Spinner variant="primary" />
                        : <Container>
                            <Card>
                            <Card.Header>
                                <h5 className="text-center">Ticket status: <Badge className='justify-content-center mx-2' pill text={ticket.status === "IN_PROGRESS" ? "dark" : null} bg={mapStatus(ticket.status)}>{ticket.status}</Badge></h5>
                            </Card.Header>
                            <Card.Body>
                            {props.messages.length !== 0
                                    ? <ListGroup className="mt-2 mb-2">
                                    {props.messages.map(m => <MessageItem user={props.user} key={m.id} message={m}></MessageItem>)}
                                    </ListGroup>
                                    : <h4 className="text-center">No messages found</h4>}
                            </Card.Body>
                            </Card>
                            {ticket.status === "IN_PROGRESS"
                                ? <Form className="d-flex justify-content-center py-3" onSubmit={event => { event.preventDefault() }} >
                                    <Form.Group className="w-100">
                                        <InputGroup>
                                            <Form.Control type="text" placeholder="Your answer.." value={send} onChange={event => setSend(event.target.value)} />
                                            {send !== "" ? <Button disabled={ticket.status !== "IN_PROGRESS"} onClick={() => handleSend()}>Send</Button> : null}
                                        </InputGroup>
                                    </Form.Group>
                                </Form> : null}
                        </Container>
                    }
                </Container>}
        </>
    )
}

export default MessageConversation;