import { Badge, Card, Col, Row } from "react-bootstrap";
import statusArray from "../../utils/StatusArray";
import mapStatus from "../../utils/MapStatus";

function TicketStats(props) {
    return(
        <>
            <Row>
                {statusArray.map((status, index) => {
                    return <Col>
                            <Card onClick={() => props.onItemClick(status === "REOPENED" ? '' : status)} style={{cursor:"pointer"}}>
                                <Card.Header>
                                <Badge pill text={status === "IN_PROGRESS" ? "dark" : null} bg={mapStatus(status)}>{status === "REOPENED" ? "ALL" : status}</Badge>
                                </Card.Header>
                                <Card.Body>
                                    <Card.Title>
                                        {
                                          status === "REOPENED" 
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