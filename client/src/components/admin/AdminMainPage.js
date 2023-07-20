import { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import API from "../../API";

function AdminMainPage(props){
    const [experts,setExperts] = useState(null);
    const [expertWait,setExpertWait] = useState(true);
    useEffect(()=>{
        if(experts == null){
            API.getExperts().then((res)=>{
                setExperts(res);
            }).catch(err => {
                Notification.showError(err.detail);
                setExperts([]);
            }).finally(()=>{
                setExpertWait(false)
            })

        }
    },[])

    return <>
            <Row>
                <Col>Welcome Admin!</Col>
            </Row>
            {
                expertWait ? 
                    <Row>Waiting for server response</Row>
                : 
                    <expertTable experts={experts}/>
            }
        </>
}

function expertTable(props){
    return <>
    <Col>
    <Row>
        Id
    </Row>
    <Row>
        First Name
    </Row>
    <Row>
        Last Name
    </Row>
    <Row>
        Email
    </Row>
    </Col>
        {props.experts.map((expert)=>{
            <Col>
            <Row>
                {expert.id}
            </Row>
            <Row>
                {expert.firstName}
            </Row>
            <Row>
                {expert.lastName}
            </Row>
            <Row>
                {expert.email}
            </Row>
            </Col>
        })}</>
}

export default AdminMainPage;