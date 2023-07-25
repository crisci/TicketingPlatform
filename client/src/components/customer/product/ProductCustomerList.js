import { Container, ListGroup } from "react-bootstrap"

function ProductCustomerList(props) {


    return (
        <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="title" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>EAN</Container>
                <Container>Name</Container>
                <Container>Brand</Container>
            </ListGroup.Item>
            {
                props.products
                    .filter(p => 
                        p.name.toLowerCase().startsWith(props.nameFilter.toLowerCase())
                        || p.brand.toLowerCase().startsWith(props.nameFilter.toLowerCase())
                        || p.ean.startsWith(props.nameFilter))
                    .map(product =>
                            <ListGroup.Item key={product.ean} as='li' className="d-flex justify-content-beetween mb-3 py-3">
                                <Container>{product.ean}</Container>
                                <Container>{product.name}</Container>
                                <Container>{product.brand}</Container>
                            </ListGroup.Item>)
            }
        </ListGroup>
    )
}

export default ProductCustomerList;