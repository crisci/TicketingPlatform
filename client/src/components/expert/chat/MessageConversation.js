import { Card, ListGroup, Container, Form, Button, InputGroup, Spinner, Row, Badge } from "react-bootstrap";
import { useEffect, useState } from "react";
import MessageItem from "./MessageItem";
import { useParams } from "react-router-dom";
import mapStatus from "../../../utils/MapStatus";

function MessageConversation(props) {

    const params = useParams()

    const [ticket, setTicket] = useState({})
    const [send, setSend] = useState("")
    const [attachments, setAttachments] = useState([])

    useEffect(() => {
        setTicket(props.tickets.find(t => t.id.toString() === params.id))
        props.getMessages(params.id)
        // eslint-disable-next-line
    }, [])


    const handleSend = () => {
            // Create an array of attachment objects
            const listOfAttachments = attachments.map((attachment) => ({
                attachment: attachment,
            }));

          
        props.addMessage(params.id, send, listOfAttachments)
        setSend("")
        setAttachments([])
    }
    const handleFileChange = (event) => {
        const file = event.target.files[0]; // Get the selected file from the input
        console.log("Reading file...");
        if (file) {
            const reader = new FileReader();
    
            // Read the file as a binary string
            reader.readAsArrayBuffer(file);
    
            // When the file is loaded, convert it to a base64 data URL
            reader.onload = () => {
                const byteArray = new Uint8Array(reader.result);
                const base64String = btoa(String.fromCharCode.apply(null, byteArray));
                const fileType = file.type;

                // Check if the file type is jpg, jpeg, or png before setting the attachment
            if (fileType === "image/jpeg" || fileType === "image/jpg" || fileType === "image/png" || fileType === "application/pdf") {
                const attachment = `data:${fileType};base64,${base64String}`;
                setAttachments([attachment]); // Update the state with the attachment data URL
            } else {
                Notification.error("Invalid file type. Only JPG, JPEG, PNG or PDF files are allowed.");
                // Optionally, you can provide feedback to the user about the invalid file type here.
            }
            };
        }
    };
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
                                            {props.messages.map(m => <MessageItem ticket={ticket} user={props.user} key={m.id} message={m}></MessageItem>)}
                                        </ListGroup>
                                        : <h4 className="text-center">No messages found</h4>}
                                </Card.Body>
                            </Card>
                            {ticket.status === "IN_PROGRESS"
                                ? <Form className="justify-content-center py-3" onSubmit={event => { event.preventDefault() }} >
                                    <Form.Group className="w-100">
                                        <InputGroup>
                                            <Form.Control type="text" placeholder="Your answer.." value={send} onChange={event => setSend(event.target.value)} />
                                            {send !== "" || attachments.length > 0 ? <Button disabled={ticket.status !== "IN_PROGRESS"} onClick={() => handleSend()}>Send</Button> : null}
                                        </InputGroup>
                                    </Form.Group>
                                    <Form.Group controlId="formFile" onChange={handleFileChange}>
                                        <Form.Control type="file" />
                                    </Form.Group>
                                </Form> : null}
                        </Container>
                    }
                </Container>}
        </>
    )
}

export default MessageConversation;
