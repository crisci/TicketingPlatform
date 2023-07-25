import { useEffect, useState } from "react";
import { Card, Container, ListGroup, Row } from "react-bootstrap";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import API from "../../API";
import Notification from "../../utils/Notifications";
import Divider from '@mui/material/Divider'
import formatDateTime from "../../utils/DateTimeFormatter";
import MessageItem from "../expert/chat/MessageItem";

function TicketDetails(props) {
    const navigate = useNavigate();
    const params = useParams();
    const { state } = useLocation();
    const ticketId = params.ticketId;
    const { expertId, customer, product, ticket } = state ? state : { expertId: undefined, customer: undefined, product: undefined, ticket: undefined }
    const [legalState, setLegalState] = useState(true);
    const [histories, setHistories] = useState([]);
    const [historyMsg, setHistoryMsg] = useState("loading");
    const [historyWait, setHistoryWait] = useState(true);
    const [messages, setMessages] = useState();
    const [messagesMsg, setMessagesMsg] = useState("loading");
    const [messagesWait, setMessagesWait] = useState(true);

    useEffect(() => {
        setLegalState(() => (ticket !== undefined && ticketId !== undefined && product !== undefined && customer !== undefined && Number(ticketId) === Number(ticket.id)))
        if (ticket !== undefined && ticketId !== undefined && product !== undefined && customer !== undefined && Number(ticketId) === Number(ticket.id)) {
            API.getTicketHistory(Number(ticketId))
                .then((histories) => {
                    setHistories(histories);
                    setHistoryWait(false);
                })
                .catch((err) => {
                    Notification.showError(err.detail);
                    setHistoryMsg("can't reach the server")
                })
            API.getTicketMessage(Number(ticketId))
                .then((messages) => {
                    setMessages(messages);
                    setMessagesWait(false);
                })
                .catch((err) => {
                    Notification.showError(err.detail);
                    setMessagesMsg("can't reach the server")
                })
        }
    }, [ticketId, ticket, ticket.id, product, customer])

    return legalState ? <>
        <Container className="mt-3">
            <Container className="d-flex justify-content-center align-items-center">
                <h2 className="m-0">Ticket</h2>
            </Container>
            <Row key="r1" className="d-flex justify-content-center mt-4">
                <ListGroup key="lg1" variant="flush" className="px-3">
                    <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                        <Container>Id</Container>
                        <Container>Title</Container>
                        <Container>Description</Container>
                        <Container>Priority</Container>
                    </ListGroup.Item>
                    <ListGroup.Item key="attr1" as='li' className="d-flex justify-content-beetween mb-3 py-3">
                        <Container>{ticketId}</Container>
                        <Container>{ticket.title}</Container>
                        <Container>{ticket.description}</Container>
                        <Container>{ticket.priority}</Container>
                    </ListGroup.Item>
                </ListGroup>
            </Row>
        </Container>
        <Container style={{ 'backgroundColor': '#ccffff', 'borderRadius': '2rem' }}>
            <Container className="mt-3">
                <Container className="d-flex justify-content-center align-items-center">
                    <h2 className="m-0">Details:</h2>
                </Container>
                <br /><br />
                <Container className="d-flex justify-content-center align-items-center">
                    <h3 className="m-0">Customer</h3>
                </Container>
                <Container className="d-flex justify-content-center align-items-center">
                    <h5 className="m-0"><i> - who opened the ticket - </i></h5>
                </Container>
                <Row key="r2" className="d-flex justify-content-center mt-4">
                    <ListGroup key="lg2" variant="flush" className="px-3">
                        <ListGroup.Item key="attr2" as='li' className="d-flex justify-content-beetween list-titles">
                            <Container>Id</Container>
                            <Container>First Name</Container>
                            <Container>Last Name</Container>
                            <Container>Email</Container>
                            <Container>Date Of Birthday</Container>
                            <Container>Address</Container>
                            <Container>Phone</Container>
                        </ListGroup.Item>
                        <ListGroup.Item key="attr3" as='li' className="d-flex justify-content-beetween mb-3 py-3">
                            <Container>{customer.id}</Container>
                            <Container>{customer.firstName}</Container>
                            <Container>{customer.lastName}</Container>
                            <Container>{customer.email}</Container>
                            <Container>{customer.dob}</Container>
                            <Container>{customer.address}</Container>
                            <Container>{customer.phoneNumber}</Container>
                        </ListGroup.Item>
                    </ListGroup>
                </Row>
            </Container>
            <Container className="mt-3">
                <Container className="d-flex justify-content-center align-items-center">
                    <h3 className="m-0">Product</h3>
                </Container>
                <Container className="d-flex justify-content-center align-items-center">
                    <h5 className="m-0"><i> - interested from the ticket - </i></h5>
                </Container>
                <Row key="r3" className="d-flex justify-content-center mt-4">
                    <ListGroup key="lg3" variant="flush" className="px-3">
                        <ListGroup.Item key="attr4" as='li' className="d-flex justify-content-beetween list-titles">
                            <Container>EAN</Container>
                            <Container>Name</Container>
                            <Container>Brand</Container>
                        </ListGroup.Item>
                        <ListGroup.Item key="attr5" as='li' className="d-flex justify-content-beetween mb-3 py-3">
                            <Container>{product.ean}</Container>
                            <Container>{product.name}</Container>
                            <Container>{product.brand}</Container>
                        </ListGroup.Item>
                    </ListGroup>
                </Row>
            </Container>
            {
                historyWait ? historyMsg :
                    <><Container className="mt-3">
                        <Container className="d-flex justify-content-center align-items-center">
                            <h3 className="m-0">History of the Ticket</h3>
                        </Container>
                        <Container className="d-flex justify-content-center align-items-center">
                            <h5 className="m-0"><i> - from newest to oldest - </i></h5>
                        </Container>
                        <Row key="r4" className="d-flex justify-content-center mt-4">
                            <ListGroup key="lg4" variant="flush" className="px-3">
                                <ListGroup.Item key="attr6" as='li' className="d-flex justify-content-beetween list-titles">
                                    <Container>State</Container>
                                    <Container>Date</Container>
                                    <Container>Employee Id</Container>
                                    <Container>Employee Email</Container>
                                </ListGroup.Item>
                                {histories.map((history, index) =>
                                    <ListGroup.Item key={`history${history.id}`} as='li' className="d-flex justify-content-beetween mb-3 py-3">
                                        <Container>{history.state}</Container>
                                        <Container>{formatDateTime(history.date)}</Container>
                                        <Container>{history.employee ? history.employee.id : ""}</Container>
                                        <Container>{history.employee ? history.employee.email : ""}</Container>
                                    </ListGroup.Item>
                                )}
                            </ListGroup>
                        </Row>
                    </Container></>
            }
            <br />
            {
                messagesWait ? messagesMsg :
                    <><Container className="mt-3">
                        <Container className="d-flex justify-content-center align-items-center">
                            <h3 className="m-0">Messages</h3>
                        </Container>
                        <Row key="r5" className="d-flex justify-content-center mt-4">
                            {messages.length !== 0
                                ? <ListGroup key="lg5" variant="flush" className="px-3">
                                    {messages.sort((a, b) => a.date > b.date).map((m, index) => <Container key={index}>
                                        <MessageItem ticket={ticket} user={props.user} key={m.id} message={m}></MessageItem>
                                    </Container>)}
                                </ListGroup>
                                : <h4>No messages</h4>}
                        </Row>
                    </Container>
                    </>
            }
        </Container></> : navigate("/")
}
export default TicketDetails;