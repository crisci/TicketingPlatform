import { Container, ListGroup, OverlayTrigger, Tooltip } from "react-bootstrap";
import { BsXCircleFill } from "react-icons/bs";

function ProductList(props) {

    function handleX(ean) {
        props.removeProduct(ean)
    }

    const tooltip = (
        <Tooltip id="tooltip">
            Remove Product
        </Tooltip>
    )

    return (
        <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="title" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>EAN</Container>
                <Container>Name</Container>
                <Container>Brand</Container>
                <Container>Actions</Container>
            </ListGroup.Item>
            {
                props.products.map(product =>
                    <ListGroup.Item key={product.ean} as='li' className="d-flex justify-content-beetween mb-3 py-3">
                        <Container>{product.ean}</Container>
                        <Container>{product.name}</Container>
                        <Container>{product.brand}</Container>
                        <Container ><OverlayTrigger placement="top" overlay={tooltip}>
                            <div style={{ cursor: 'pointer' }} onClick={() => handleX(product.ean)}>
                                <BsXCircleFill color="red" size="20px" />
                            </div>
                        </OverlayTrigger></Container>
                    </ListGroup.Item>)
            }
        </ListGroup>
    )
}

export default ProductList;