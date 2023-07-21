import { useEffect, useState } from "react";
import { Container, ListGroup, Row } from "react-bootstrap";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import API from "../../API";
import Notification from "../../utils/Notifications";

function TicketDetails(){
    const navigate = useNavigate();
    const params = useParams();
    const {state} = useLocation();
    const ticketId = params.ticketId;
    const {expertId,customer,product,ticket} = state;
    const [legaState,setLegalState] = useState(true);
    const [histories,setHistories] = useState();
    const [historyMsg,setHistoryMsg] = useState("loading");
    const [historyWait,setHistoryWait] = useState(true);
    const [messages,setMessages] = useState();
    const [messagesMsg,setMessagesMsg] = useState("loading");
    const [messagesWait,setMessagesWait] = useState(true);

    useEffect(()=>{
        setLegalState(()=>(Number(ticketId)===Number(ticket.id) && product!==undefined && customer!==undefined))
        if(Number(ticketId)===Number(ticket.id) && product!==undefined && customer!==undefined){
            API.getTicketHistory(Number(ticketId))
                .then((histories)=>{
                    setHistories(histories);
                    setHistoryWait(false);
                })
                .catch((err)=>{
                    Notification.showError(err.detail);
                    setHistoryMsg("can't reach the server")
                })
            API.getTicketMessage(Number(ticketId))
                .then((messages)=>{
                    setMessages(messages);
                    setMessagesWait(false);
                })
                .catch((err)=>{
                    Notification.showError(err.detail);
                    setMessagesMsg("can't reach the server")
                })
        }
    },[ticketId,ticket.id,product,customer])
    
    return legaState ? <>
    <Container className="mt-3">
        <Container className="d-flex justify-content-center align-items-center">
            <h2 className="m-0">ticket</h2>
        </Container>
        <Row className="d-flex justify-content-center mt-4">
    <ListGroup variant="flush" className="px-3">
        <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>Id</Container>
                <Container>Title</Container>
                <Container>Description</Container>
                <Container>Priority</Container>
                <Container>Expert Id</Container>
                <Container>Customer Id</Container>
                <Container>Product Id</Container>
        </ListGroup.Item>
        <ListGroup.Item key="attr1" as='li' className="d-flex justify-content-beetween mb-3 py-3">
            <Container>{ticketId}</Container>
            <Container>{ticket.title}</Container>
            <Container>{ticket.description}</Container>
            <Container>{ticket.priority}</Container>
            <Container>{expertId}</Container>
            <Container>{customer.id}</Container>
            <Container>{product.ean}</Container>
        </ListGroup.Item>
    </ListGroup>
    </Row>
    </Container>
    <Container className="mt-3">
        <Container className="d-flex justify-content-center align-items-center">
            <h4 className="m-0">customer</h4>
        </Container>
        <Row className="d-flex justify-content-center mt-4">
    <ListGroup variant="flush" className="px-3">
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
            <h4 className="m-0">product</h4>
        </Container>
        <Row className="d-flex justify-content-center mt-4">
    <ListGroup variant="flush" className="px-3">
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
            <h4 className="m-0">ordered history</h4>
        </Container>
        <Row className="d-flex justify-content-center mt-4">
            <ListGroup variant="flush" className="px-3">
                <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                        <Container>Id</Container>
                        <Container>State</Container>
                        <Container>Date</Container>
                        <Container>Employee Id</Container>
                        <Container>Employee Email</Container>
                </ListGroup.Item>
                    {histories.map((history)=>
                        <ListGroup.Item key="attr1" as='li' className="d-flex justify-content-beetween mb-3 py-3">
                        <Container>{history.id}</Container>
                        <Container>{history.state}</Container>
                        <Container>{history.date}</Container>
                        <Container>{history.employee.id}</Container>
                        <Container>{history.employee.email}</Container>
                        </ListGroup.Item>
                    )}
            </ListGroup>
        </Row>
    </Container></>
    }
    <br/>
    {
        messagesWait ? messagesMsg :
        <><Container className="mt-3">
        <Container className="d-flex justify-content-center align-items-center">
            <h4 className="m-0">messages</h4>
        </Container>
        <Row className="d-flex justify-content-center mt-4">
            <ListGroup variant="flush" className="px-3">
                <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                        <Container>Id</Container>
                        <Container>Body</Container>
                        <Container>Date</Container>
                        <Container>List Of Attachments</Container>
                        <Container>Expert Id</Container>
                </ListGroup.Item>
                {messages.map((message)=>
                    <ListGroup.Item key="attr1" as='li' className="d-flex justify-content-beetween mb-3 py-3">
                    <Container>{message.id}</Container>
                    <Container>{message.body}</Container>
                    <Container>{message.date}</Container>
                    <Container>{message.listOfAttachments}</Container>
                    <Container>{message.expert}</Container>
                    </ListGroup.Item>
                )}
            </ListGroup>
        </Row>
    </Container></>
    }
    </> : navigate("/")
}
export default TicketDetails;