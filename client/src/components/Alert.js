import Alert from 'react-bootstrap/Alert';

function AlertDismissibleExample(props) {
    return (
      <Alert className="p-2" variant="danger" onClose={() => props.resetError()} dismissible>
        <Alert.Heading>{props.title}</Alert.Heading>        
        {props.description}
      </Alert>
    );
}

export default AlertDismissibleExample;