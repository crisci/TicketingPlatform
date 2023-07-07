import { useState } from "react";
import { Alert, Button, Container, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Notification from "../../../utils/Notifications";


function TicketForm(props) {

    const navigate = useNavigate()

    const [title, setTitle] = useState("")
    const [description, setDescription] = useState("")
    const [priority, setPriority] = useState("")
    const [product, setProduct] = useState("")


    function resetStates() {
        setTitle("")
        setDescription("")
        setPriority("")
        setProduct("")
    }

    function handleCancel() {
        resetStates()
        navigate("/")
    }

    function handleConfirm() {
        if (title === "" || description === "" || priority === "" || product === "") {
            Notification.showError("Please fill all the fields!")
            return
        }
        if (title.length > 255) {
            Notification.showError("Title length must be less than 255 characters!")
            return
        }
        if (description.length > 1000) {
            Notification.showError("Description length too long!")
            return
        }
        const ticket = { id: 0, title, description, priority, dateTime: new Date(), product: JSON.parse(product) }
        props.openTicket(ticket)
        navigate("/")
        resetStates()

    }

    return (
        <Container className="w-50">
            {props.products.length === 0
                ? <Alert variant="warning">
                    <Alert.Heading>Warning!</Alert.Heading>
                    <p>
                        It seems that you don't have any product registered. Please register a product before opening a ticket.
                    </p>
                </Alert> : null}
            <Form onSubmit={event => { event.preventDefault() }}>
                <Form.Group className="mb-3" controlId='title'>
                    <Form.Label>Title</Form.Label>
                    <Form.Control type="username" value={title} maxLength={255} onChange={(event) => { setTitle(event.target.value) }} />
                </Form.Group>
                <Form.Group className="mb-3" controlId="description">
                    <Form.Label>Description</Form.Label>
                    <Form.Control value={description} as="textarea" aria-label="With textarea" maxLength={1000} style={{ height: "6rem" }} onChange={(event) => { setDescription(event.target.value) }} />
                </Form.Group>
                <Form.Group className="mb-3" controlId='priority'>
                    <Form.Label>Priority</Form.Label>
                    <Form.Select value={priority} onChange={(event) => { setPriority(event.target.value) }}>
                        {priority === "" ? <option key={"default"}>Select the priority</option> : null}
                        <option key={"low"} value="LOW">Low</option>
                        <option key={"medium"} value="MEDIUM">Medium</option>
                        <option key={"high"} value="HIGH">High</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group className="mb-3" controlId='product'>
                    <Form.Label>Product</Form.Label>
                    <Form.Select disabled={props.products.length === 0 ? true : false} onChange={(event) => { setProduct(event.target.value) }}>
                        {product === "" ? <option key={"default"}>Select the product</option> : null}
                        {props.products.map(p => { return <option key={p.name} value={JSON.stringify(p)}>{p.name}</option> })}
                    </Form.Select>
                </Form.Group>
                <Container className="d-flex p-0 pt-3 justify-content-between">
                    <Button variant="danger" onClick={handleCancel}>Cancel</Button>
                    <Button disabled={props.products.length === 0 ? true : false} variant="primary" onClick={handleConfirm}>Confirm</Button>
                </Container>
            </Form>
        </Container>
    )
}

export default TicketForm;