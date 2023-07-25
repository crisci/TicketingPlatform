import { Container, Form, Row, Spinner } from "react-bootstrap";
import TicketList from "./TicketList";
import { useState, useEffect } from "react";
import TicketStats from "./TicketsStats";

function ExpertTickets(props) {

    const [nameFilter, setNameFilter] = useState("")
    const [spin, setSpin] = useState(true)
    const [selectedStatus, setSelectedStatus] = useState("");

    const onItemClick = (item) => {
        setSelectedStatus(item);
    };

    const REFRESH = 5000;

    useEffect(() => {
        const interval = setInterval(() => {
            console.log("tickets")
            props.getTickets(props.user)
        }, REFRESH);

        return() => clearTimeout(interval);// This represents the unmount function, in which you need to clear your interval to prevent memory leaks.
        // eslint-disable-next-line
    }, []);

    useEffect(() => { //Use spin state instead of loadingTickets to avoid Spinner animation at every poll
        if(props.loadingTickets === false && spin === true){
            setSpin(false);
        }
        // eslint-disable-next-line
    }, [props.loadingTickets]);

    return (
        <Container className="mt-3">
            <Container className="d-flex justify-content-center align-items-center mb-4">
                <h1 className="m-0">Expert Tickets</h1>
            </Container>
            <TicketStats tickets={props.tickets} onItemClick={onItemClick} />
            <Form className="d-flex justify-content-center pt-3">
                <Form.Group className="w-50">
                    <Form.Control type="text" placeholder="Search..." value={nameFilter} onChange={event => setNameFilter(event.target.value)}/>
                </Form.Group>
            </Form>
            <Row className="d-flex justify-content-center mt-4">
                {!spin 
                    ? <TicketList selectedStatus={selectedStatus} tickets={selectedStatus === "" ? props.tickets.filter(t => ["IN_PROGRESS", "CLOSED", "RESOLVED"].includes(t.status)) : props.tickets.filter(t => t.status === selectedStatus)} messages={props.messages} loadingMessages={props.loadingMessages} nameFilter={nameFilter} getMessages={props.getMessages} stopTicket={props.stopTicket}/> 
                    : <Spinner  variant="primary"/>        
                }
            </Row>
        </Container>
    )
}

export default ExpertTickets;
