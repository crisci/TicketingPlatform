import { Card, ListGroup, Container, Form, Button, InputGroup, Spinner, Row, Badge } from "react-bootstrap";
import { useEffect, useState } from "react";
import MessageItem from "./MessageItem";
import { useParams } from "react-router-dom";
import mapStatus from "../../../utils/MapStatus";
import Notification from "../../../utils/Notifications";

function MessageConversation(props) {

    const params = useParams()

    const [ticket, setTicket] = useState({})
    const [send, setSend] = useState("")
    const [attachments, setAttachments] = useState([])
    const [spin, setSpin] = useState(true)

    const REFRESH = 1000;

    useEffect(() => {
        setTicket(props.tickets.find(t => t.id.toString() === params.id))
        props.getMessages(params.id)
        const interval = setInterval(() => {
            props.getMessages(params.id)
        }, REFRESH);
      
        return() => clearTimeout(interval);// This represents the unmount function, in which you need to clear your interval to prevent memory leaks.
        // eslint-disable-next-line
    }, [])

    useEffect(() => { //Use spin state instead of loadingMessages to avoid Spinner animation at every poll
        if(props.loadingMessages === false && spin === true){
            setSpin(false);
        }
        // eslint-disable-next-line
    }, [props.loadingMessages]);


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
        const files = event.target.files // Get the selected file from the input
        setAttachments([]);
        if (files) {
            if(files.length <= 3){
                Array.from(files).forEach((file) => {
                    const reader = new FileReader();
        
                    // Read the file as a binary string
                    reader.readAsArrayBuffer(file);
            
                    // When the file is loaded, convert it to a base64 data URL
                    reader.onload = () => {
                        const byteArray = new Uint8Array(reader.result);
                        const base64String = arrayBufferToBase64(byteArray);
                        const fileType = file.type;
        
                        // Check if the file type is jpg, jpeg, or png before setting the attachment
                    if (fileType === "image/jpeg" || fileType === "image/jpg" || fileType === "image/png" || fileType === "application/pdf") {
                        const attachment = `data:${fileType};base64,${base64String}`;
                        setAttachments(a => [...a, attachment]); // Update the state with the attachment data URL
                    } else {
                        Notification.showError("Invalid file type. Only JPG, JPEG, PNG or PDF files are allowed.");
                        event.target.value = null;
                        setAttachments([]);
                    }
                    };
                });
            }else{
                Notification.showError("Max 3 files are permitted");
                event.target.value = null;
                setAttachments([]);
            }
        }
    };

    function arrayBufferToBase64(buffer) {
        let binary = "";
        const bytes = new Uint8Array(buffer);
        const len = bytes.byteLength;
        for (let i = 0; i < len; i++) {
          binary += String.fromCharCode(bytes[i]);
        }
        return btoa(binary);
      }


    return (
        <>
            {ticket === undefined
                ? <Spinner variant="primary" />
                : <Container>
                    <h2 className="text-center">{ticket.title}</h2>
                    <p className="text-center">{ticket.description}</p>
                    <Row className="d-flex justify-content-center"><Button className="mb-3 w-25" variant="danger" onClick={() => props.handleCloseChat()}>Close chat</Button></Row>
                    {spin === true
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
                                    <Form.Group>
                                        <Form.Control type="file" id="fileForm" multiple onChange={handleFileChange}/>
                                    </Form.Group>
                                </Form> : null}
                        </Container>
                    }
                </Container>}
        </>
    )
}

export default MessageConversation;
