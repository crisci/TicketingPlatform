import { useEffect, useState } from "react";
import { Button, Container, Form, ListGroup, Row } from "react-bootstrap";
import API from "../../API";
import Notification from '../../utils/Notifications';
import { useNavigate } from "react-router-dom";


function AdminMainPage(props){
    const [expertMsg,setExpertMsg] = useState("Waiting for server response");
    const [experts,setExperts] = useState(null);
    const [expertWait,setExpertWait] = useState(true);

    const [ticketMsg,setTicketMsg] = useState("Waiting for server response");
    const [tickets,setTickets] = useState(null);
    const [ticketWait,setTicketWait] = useState(true);

    const approvedExpert = (expertId) => {
        setExperts((old)=>old.map((ex)=>{
            if(ex.id===expertId){
                ex.approved = true;
            }
            return ex;
        }))
    }

    useEffect(()=>{
        if(!experts){
            API.getExperts().then((res)=>{
                setExperts(res);
                setExpertMsg("Waiting for server response");
                setExpertWait(false)
            }).catch(err => {
                Notification.showError(err.detail);
                setExpertMsg("Error trying to contact the server.");
            })
        }

        if(!tickets){
            API.getManagerTickets().then((res)=>{
                setTickets(res);
                setTicketMsg("Waiting for server response");
                setTicketWait(false)
            }).catch(err => {
                Notification.showError(err.detail);
                setTicketMsg("Error trying to contact the server.");
            });
            
        }
    },[experts,tickets])

    return <>
    <Container className="mt-3">
        <Container className="d-flex justify-content-center align-items-center">
            <h1 className="m-0">List of Experts</h1>
        </Container>
        <Row className="d-flex justify-content-center mt-4">
        {
            expertWait ? 
                expertMsg
            : 
                <ExpertTable experts={experts} approvedExpert={approvedExpert}/>
        }
        </Row>
    </Container>
    <br/>
    <Container className="mt-3">
        <Container className="d-flex justify-content-center align-items-center">
            <h1 className="m-0">List of Tickets</h1>
        </Container>
        <Row className="d-flex justify-content-center mt-4">
        {
            ticketWait ? 
                ticketMsg
            : 
                <TicketTable tickets={tickets} experts={experts}/>
        }
        </Row>
    </Container>
    </>
}

function ExpertTable(props){
    return <>
    <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>Id</Container>
                <Container>First Name</Container>
                <Container>Last Name</Container>
                <Container>Email</Container>
                <Container>Approved</Container>
                <Container>Approve</Container>
    </ListGroup.Item>
    { !props.experts || props.experts.length === 0 ?
           <h2>0 experts found.</h2>
        :  props.experts.map((expert)=><ExpertItem key={expert.id} expert={expert} approvedExpert={props.approvedExpert} />)}
    </ListGroup>
    </>
}

function ExpertItem(props){
    const approveExpert = (expertId) => {
        API.approveExpert(expertId)
            .then(()=>{
                props.approvedExpert(expertId);
                Notification.showSuccess("Approved!")
            })
            .catch((err)=>Notification.showError("Can't reach the server"))
    }
    return (

        <ListGroup.Item key={props.expert.id} as='li' className="d-flex justify-content-beetween mb-3 py-3">
            <Container>{props.expert.id}</Container>
            <Container>{props.expert.firstName}</Container>
            <Container>{props.expert.lastName}</Container>
            <Container>{props.expert.email}</Container>
            <Container>{props.expert.approved ? 'Approved' : 'Not Approved'}</Container>
            <Container>
                { !props.expert.approved &&
                <Button variant="outline-success" onClick={()=>approveExpert(props.expert.id)}>
                    Approve
                </Button> }
            </Container>
        </ListGroup.Item>
    )
}


function TicketTable(props){
    return <>
    <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>Id</Container>
                <Container>Title</Container>
                <Container>Description</Container>
                <Container>Priority</Container>
                <Container>Current Expert</Container>
                <Container>Select Expert</Container>
                <Container>Confirm New Expert</Container>
                <Container>Details</Container>
        </ListGroup.Item>
        { props.tickets.length === 0 ?
            <h2>0 tickets found.</h2>
            :  props.tickets.map((ticket,index)=><TicketItem key={index} index={index} ticket={ticket} experts={props.experts}/>)}
    </ListGroup>
    </>
}

function TicketItem(props){
    const navigate = useNavigate();
    const [expertMsg,setExpertMsg] = useState("Waiting response")
    const [expertWait,setExpertWait] = useState(true);
    const [currentExpert,setCurrentExpert] = useState('');
    const [selectedExpert,setSelectedExpert] = useState();
    const [render,setRender] = useState(false);
    useEffect(()=>{
        if(props.ticket && props.ticket.id !== undefined ){
        API.getTicketCurrentExpert(props.ticket.id)
            .then((expert)=>{
                setCurrentExpert(expert);
                setSelectedExpert(expert.id);
                setExpertWait(false);
            })
            .catch((err)=>{
                Notification.showError(err.detail);
                setExpertMsg("Error contacting the server.");
            })
        }
    },[props.ticket,props.ticket.id,render])

    const submitNewExpert = (id) => {
        API.reasignTicket(props.ticket.id,id)
            .then(()=>Notification.showSuccess("Ticket reassigned"))
            .catch((err)=>Notification.showError(err.detail))
            .finally(()=>setRender((old)=>!old))
    }

    return (

        <ListGroup.Item key={props.index} as='li' className="d-flex justify-content-beetween mb-3 py-3">
            <Container>{props.ticket.id}</Container>
            <Container>{props.ticket.title}</Container>
            <Container>{props.ticket.description}</Container>
            <Container>{props.ticket.priority}</Container>
            <Container>{expertWait || !props.experts ? expertMsg : currentExpert.id}</Container>
            <Container>
                <Form.Select defaultValue={currentExpert ? currentExpert : ''} onChange={(event)=>{setSelectedExpert(event.target.value)} }>
                    <option key="-1"></option>
                    {props.experts ? props.experts.map((expert)=>
                        <option key={expert.id}>
                            {expert.id}
                        </option>
                    ):<></>}
                </Form.Select>
            </Container>
            <Container>
                { !selectedExpert  ? "select an expert" :
                <Button variant="outline-success" onClick={()=>submitNewExpert(selectedExpert)}>
                    reassign
                </Button>
                }
            </Container>
            <Container><Button variant="outline-primary" onClick={()=>navigate(`/${props.ticket.id}/details`,{state:{expertId:currentExpert.id,customer:props.ticket.customer,product:props.ticket.product,ticket:props.ticket}})}>
                    details
                </Button></Container>
        </ListGroup.Item>
    )
}

export default AdminMainPage;