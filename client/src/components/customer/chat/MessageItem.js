import './custommessages.css'
import { Container, ListGroup } from "react-bootstrap";

function MessageItem(props) {

    return (
        <Container>
            {props.message.expert != null 
            ?   
            <>
                <span className="d-flex justify-content-left">Expert says: &nbsp;</span>
                <Container className="d-flex justify-content-left">
                    <ListGroup.Item key={props.message.id} as='li' className="d-flex mb-3 py-3 w-75">
                        <Container>{props.message.body}</Container>
                    </ListGroup.Item>
                </Container>
            </>
            :   
            <>
                <Container className="d-flex justify-content-end">
                    <ListGroup.Item active key={props.message.id} as='li' className="d-flex mb-3 py-3 w-75">
                        <Container>{props.message.body}</Container>
                    </ListGroup.Item>
                </Container>
            </>
            }
        </Container>
    )
}

export default MessageItem;