import { useEffect, useState } from "react";
import API from "../../API";
import { Container, ListGroup, Row } from "react-bootstrap";
import Notification from "../../utils/Notifications";

function ExpertList(){
    const [expertMsg, setExpertMsg] = useState("Waiting for server response");
    const [experts, setExperts] = useState(null);
    const [expertWait, setExpertWait] = useState(true);

    useEffect(()=>{
        API.getExperts().then((res) => {
            setExperts(res);
            setExpertMsg("Waiting for server response");
            setExpertWait(false)
        }).catch(err => {
            Notification.showError(err.detail);
            setExpertMsg("Error trying to contact the server.");
        })
    },[])
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
                    <ExpertTable experts={experts}/>
            }
            </Row>
        </Container>
    </>
}

function ExpertTable(props) {
    return <>
        <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="attr" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>Id</Container>
                <Container>First Name</Container>
                <Container>Last Name</Container>
                <Container>Email</Container>
            </ListGroup.Item>
            {!props.experts || props.experts.length === 0 ?
                <h2>0 experts found.</h2>
                : props.experts.map((expert) => <ExpertItem key={expert.id} expert={expert}/>)}
        </ListGroup>
    </>
}

function ExpertItem(props) {
    return (

        <ListGroup.Item key={props.expert.id} as='li' className="d-flex justify-content-beetween mb-3 py-3">
            <Container>{props.expert.id}</Container>
            <Container>{props.expert.firstName}</Container>
            <Container>{props.expert.lastName}</Container>
            <Container>{props.expert.email}</Container>
        </ListGroup.Item>
    )
}

export default ExpertList;