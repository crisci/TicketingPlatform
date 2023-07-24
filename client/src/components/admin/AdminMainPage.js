import { useEffect, useState } from "react";
import { Badge, Button, Container, Form, ListGroup, Row } from "react-bootstrap";
import API from "../../API";
import Notification from '../../utils/Notifications';
import { useNavigate } from "react-router-dom";
import mapStatus from "../../utils/MapStatus";
import TicketStats from "./TicketsStats";


function AdminMainPage(props) {
    const [expertMsg, setExpertMsg] = useState("Waiting for server response");
    const [experts, setExperts] = useState(null);
    const [expertWait, setExpertWait] = useState(true);

    const [ticketMsg, setTicketMsg] = useState("Waiting for server response");
    const [tickets, setTickets] = useState(null);
    const [ticketWait, setTicketWait] = useState(true);

    const [expertTickets, setExpertToTickets] = useState({});
    
    const [assigned, setAssigned] = useState(true);
    const [spin, setSpin] = useState(true)

    const REFRESH = 5000;

    useEffect(() => {
        if (assigned) {
            setAssigned(false)
            if (!experts) {
                API.getExperts().then((res) => {
                    setExperts(res);
                    setExpertMsg("Waiting for server response");
                    setExpertWait(false)
                }).catch(err => {
                    Notification.showError(err.detail);
                    setExpertMsg("Error trying to contact the server.");
                })
            }
            API.getManagerTickets().then((res) => {
                setTickets(res);
                setTicketMsg("Waiting for server response");
                setTicketWait(false)
            }).catch(err => {
                Notification.showError(err.detail);
                setTicketMsg("Error trying to contact the server.");
            });
        }
    }, [experts, tickets, assigned])

    useEffect(() => {
        const interval = setInterval(() => {
            console.log("tickets")
            API.getManagerTickets().then((res) => {
                setTickets(res);
                setTicketMsg("Waiting for server response");
                setTicketWait(false)
            }).catch(err => {
                Notification.showError(err.detail);
                setTicketMsg("Error trying to contact the server.");
            });
        }, REFRESH);

        return() => clearTimeout(interval);// This represents the unmount function, in which you need to clear your interval to prevent memory leaks.
        // eslint-disable-next-line
    }, []);

    useEffect(() => { //Use spin state instead of loadingMessages to avoid Spinner animation at every poll
        if(props.loadingMessages === false && spin === true){
            setSpin(false);
        }
        // eslint-disable-next-line
    }, [props.loadingMessages]);

    const refreshTickets = () => {
        setAssigned(true);
    }

    return <>
        <Container className="mt-3">
            <Container className="d-flex justify-content-center align-items-center">
                <h1 className="m-0">List of Tickets</h1>
            </Container>
            <Row className="d-flex justify-content-center mt-4">
                {
                    ticketWait ?
                        ticketMsg
                        : <TicketTable tickets={tickets} experts={experts} refreshTickets={refreshTickets} />
                }
            </Row>
        </Container>
    </>
}

function TicketTable(props) {
    const [selectedStatus, setSelectedStatus] = useState("");

    const onItemClick = (item) => {
        // Handle the click event here
        setSelectedStatus(item);
    };

    return <>

        <TicketStats tickets={props.tickets} onItemClick={onItemClick} />
        {props.tickets.filter((ticket) => selectedStatus ? ticket.status === selectedStatus : true).length === 0
            ? <h4 className="mt-4">0 {selectedStatus.replace("_", " ").toLowerCase()} tickets found.</h4> 
            : <ListGroup variant="flush" className="px-3">
                <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                    <Container>Status</Container>
                    <Container>Title</Container>
                    <Container>Priority</Container>
                    <Container>Current Expert</Container>
                    <Container>Select Expert</Container>
                    <Container>Confirm New Expert</Container>
                    <Container>Details</Container>
                </ListGroup.Item>
                {props.tickets.length === 0 ?
                    <h2>0 tickets found.</h2>
                    : props.tickets.filter(ticket => selectedStatus ? ticket.status === selectedStatus : true).map((ticket, index) => <TicketItem key={ticket.id} index={ticket.id} ticket={ticket} experts={props.experts} refreshTickets={props.refreshTickets} filter={selectedStatus}/>)}
            </ListGroup>}
    </>
}

function TicketItem(props) {
    const navigate = useNavigate();
    const [expertMsg, setExpertMsg] = useState("Waiting response")
    const [expertWait, setExpertWait] = useState(true);
    const [currentExpert, setCurrentExpert] = useState('');
    const [selectedExpert, setSelectedExpert] = useState();
    const [render, setRender] = useState(false);

    
    useEffect(() => {
        if (props.ticket && props.ticket.id !== undefined) {
            API.getTicketCurrentExpert(props.ticket.id)
                .then((expert) => {
                    setCurrentExpert(expert);
                    setSelectedExpert(expert.id);
                    setExpertWait(false);
                })
                .catch((err) => {
                    Notification.showError(err.detail);
                    setExpertMsg("Error contacting the server.");
                })
        }
    }, [props.ticket, props.ticket.id])

    const submitNewExpert = (id) => {
        if (currentExpert === selectedExpert) {
            return
        }
        API.reasignTicket(props.ticket.id, id)
            .then(() => {
                Notification.showSuccess("Ticket reassigned")
                props.refreshTickets()

            })
            .catch((err) => {console.log(err);Notification.showError(err.detail);})
            .finally(() => setRender((old) => !old))
    }

    return (

        <ListGroup.Item key={props.ticket.id} as='li' className="d-flex justify-content-beetween mb-3 py-3">
            <Container><Badge pill text={props.ticket.status === "IN_PROGRESS" ? "dark" : null} bg={mapStatus(props.ticket.status)}>{props.ticket.status}</Badge></Container>
            <Container>{props.ticket.title}</Container>
            <Container>{props.ticket.priority}</Container>
            <Container>{expertWait || !currentExpert ? "Not assigned" : currentExpert.id}</Container>
            <Container>
                <Form.Select hidden={props.ticket.status === "CLOSED" || props.ticket.status === "RESOLVED"} value={selectedExpert} onChange={(event) => setSelectedExpert(event.target.value)}>
                    <option key="-1"></option>
                    {props.experts ? props.experts.map((expert) =>
                        <option key={expert.id} value={expert.id}>
                            {expert.id}
                        </option>
                    ) : <></>}
                </Form.Select>
            </Container>
            <Container>
                <Button hidden={props.ticket.status === "CLOSED" || props.ticket.status === "RESOLVED"} disabled={currentExpert.id === selectedExpert || !selectedExpert} variant="outline-success" onClick={() => submitNewExpert(selectedExpert)}>
                    {currentExpert ? "Reassign" : "Assign"}
                </Button>
            </Container>
            <Container><Button variant="outline-primary" onClick={() => navigate(`/${props.ticket.id}/details`, { state: { expertId: currentExpert.id, customer: props.ticket.customer, product: props.ticket.product, ticket: props.ticket } })}>
                Details
            </Button></Container>
        </ListGroup.Item>
    )
}


export default AdminMainPage;