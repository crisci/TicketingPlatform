import formatDateTime from '../../../utils/DateTimeFormatter';
import './custommessages.css'
import { Button, Col, Container, ListGroup } from "react-bootstrap";
import { GrDocumentPdf } from "react-icons/gr";

function MessageItem(props) {

    const Image = ({ data }) => {
        
        const handleDownload = () => {
            const link = document.createElement('a');
            link.href = data;
            link.download = 'image.jpg';
            link.click();
          };

        return (<img src={data} onClick={handleDownload} alt='' style={{cursor:"pointer"}}/>)
    }

    const Pdf = ({data}) => {
      
        const handleDownload = () => {
          const link = document.createElement('a');
          link.href = data;
          link.download = 'document.pdf';
          link.click();
        };
      
        return (
          <div className='d-flex py-2'>
            <GrDocumentPdf style={{ display: 'inline-block', width: '25px', height: '25px' }}/>
            <text style={{ display: 'inline-block', width: '100px', height: '25px', cursor:"pointer" }} onClick={handleDownload}>Download</text>
          </div>
        );
      };

    return (
        <Container>
            {props.message.expert != null
                ?
                <>
                    <Container className="d-flex justify-content-end">
                        <ListGroup.Item active key={props.message.id} as='li' className="d-flex mb-3 py-3 w-50">
                            <Col style={{ padding: 0 }}>
                                <Container className='text-start' style={{ wordBreak: 'break-all' }}>
                                    {props.message.body}
                                </Container>
                                <Container className='text-end' style={{fontSize:"0.85rem"}}>
                                    {formatDateTime(props.message.date)}
                                </Container>
                            </Col>
                        </ListGroup.Item>
                    </Container>

                </>
                :
                <>
                    <span className="d-flex justify-content-left">{`${props.user.firstName} ${props.user.lastName}`}&nbsp;</span>
                    <Container className="d-flex justify-content-start">
                        <ListGroup.Item key={props.message.id} as='li' className="d-flex mb-3 py-3 w-50">
                            <Col style={{ padding: 0 }}>
                                <Container className='text-start' style={{ wordBreak: 'break-all' }}>
                                    {props.message.body}
                                </Container>
                                <Container>
                                    {props.message.listOfAttachments.map((attachment, index) => (
                                         attachment.attachment.startsWith("data:image") 
                                         ? <div className='my-2' key={index}>
                                            <Image data={attachment.attachment} />
                                         </div>
                                         : <div className='my-2' key={index}>
                                            <Pdf data={attachment.attachment}/>
                                            </div>
                                    ))}
                                </Container>
                                <Container className='text-end' style={{fontSize:"0.85rem"}}>
                                    {formatDateTime(props.message.date)}
                                </Container>
                            </Col>
                        </ListGroup.Item>
                    </Container>

                </>
            }
        </Container>
    )
}

export default MessageItem;