import { Container, Row, Spinner, Button } from "react-bootstrap";
import { useLocation, useNavigate } from "react-router-dom";
import { BsFillCaretLeftFill } from "react-icons/bs";
import MessageConversation from "./MessageConversation";

function CustomerMessages(props) {

    const navigate = useNavigate();
    const { state } = useLocation();
    const { ticket } = state;

    return (
        <Container className="mt-3">
            <Container className="d-flex justify-content-center align-items-center">
                <Button onClick={() => {navigate("/")}}><BsFillCaretLeftFill color="white" size="20px"/></Button>
                <h2 className="m-0">{ticket.title}</h2>
            </Container>
            <Row className="d-flex justify-content-center mt-4 mx-4">
                {!props.loadingMessages ? <MessageConversation messages={props.messages} addClientMessage={props.addClientMessage} ticket={ticket}></MessageConversation> : <Spinner  variant="primary"/>}
            </Row>
        </Container>
    )
}

export default CustomerMessages;