import { Badge, Card, Col, Row } from "react-bootstrap";
import expertStatus from "../../../utils/ExpertStatus";
import mapStatus from "../../../utils/MapStatus";

function TicketStats(props) {
    return(
        <>
            <Row>
                {expertStatus.map((status, index) => {
                    return <Col key={index}>
                            <Card onClick={() => props.onItemClick(status === "ALL" ? '' : status)} style={{cursor:"pointer"}}>
                                <Card.Header>
                                <Badge pill text={status === "IN_PROGRESS" ? "dark" : null} bg={mapStatus(status)}>{status === "ALL" ? "ALL" : status}</Badge>
                                </Card.Header>
                                <Card.Body>
                                    <Card.Title>
                                        {
                                          status === "ALL" 
                                            ? props.tickets.length 
                                            : props.tickets.filter(t => t.status === status).length
                                        }
                                    </Card.Title>
                                </Card.Body>
                            </Card>

                        </Col>
                })}
            </Row>
        </>)
}

export default TicketStats;
