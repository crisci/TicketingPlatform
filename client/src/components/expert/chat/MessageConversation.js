import { Card, ListGroup, Container, Form, Button, InputGroup } from "react-bootstrap";
import { useState } from "react";
import MessageItem from "./MessageItem";
import dayjs from "dayjs";

function MessageConversation(props) {

    const reverseMessages = [...props.messages].sort((a,b) => {return new Date(a.date).getTime() - new Date(b.date).getTime()}).reverse()
    const [send, setSend] = useState("")

    return (
        <Container>
            <Card>
                <Card.Header>
                    <span>{reverseMessages.length > 0 ? "Last message: " + dayjs(reverseMessages[0].date).format("MMM DD YYYY @ HH:mm") : "No messages for this ticket"}</span>
                </Card.Header>
                <ListGroup className="mt-2 mb-2">
                    {props.messages.map(m => <MessageItem key={m.id} message={m}></MessageItem>)}
                </ListGroup>
            </Card>
            { props.ticket.status === "IN_PROGRESS"
                ?   <Form className="d-flex justify-content-center pt-3">
                <Form.Group className="w-100">
                    <InputGroup>
                        <Form.Control type="text" placeholder="Your answer.." value={send} onChange={event => setSend(event.target.value)}/>
                        {send !== "" ? <Button onClick={() => {props.addExpertMessage(props.ticket.id, send)}}>Send</Button> : null}
                    </InputGroup>
                </Form.Group>
                </Form>
                :   <h4 className="mt-2">Ticket marked as {props.ticket.status}</h4>
            }
        </Container>
    )
}

export default MessageConversation;