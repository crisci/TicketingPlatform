import { Container, Row, Spinner, Button } from "react-bootstrap";
import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { BsFillCaretLeftFill } from "react-icons/bs";
import MessageConversation from "./MessageConversation";

function ExpertMessages(props) {

    const navigate = useNavigate();
    const { state } = useLocation();
    const { ticket } = state;
    const [spin, setSpin] = useState(true);

    const REFRESH = 5000;

    useEffect(() => {
        const interval = setInterval(() => {
            props.getMessages(ticket.id);
          console.log('Logs every 5 sec');
        }, REFRESH);
      
        return() => clearTimeout(interval);// This represents the unmount function, in which you need to clear your interval to prevent memory leaks.
      }, []);

    useEffect(() => {
        if(props.loadingMessages === false && spin === true){
            setSpin(false);
            console.log('spin');
        }
    }, [props.loadingMessages]);

    return (
        <Container className="mt-3">
            <Container className="d-flex justify-content-center align-items-center">
                <Button onClick={() => {navigate("/")}}><BsFillCaretLeftFill color="white" size="20px"/></Button>
                <h2 className="m-0">{ticket.title}</h2>
            </Container>
            <Row className="d-flex justify-content-center mt-4 mx-4">
                {!spin ? <MessageConversation messages={props.messages} addExpertMessage={props.addExpertMessage} ticket={ticket}></MessageConversation> : <Spinner  variant="primary"/>}
            </Row>
        </Container>
    )
}

export default ExpertMessages;