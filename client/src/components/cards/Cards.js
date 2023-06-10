import { Card } from "react-bootstrap"

export function ProfileCard(props){
        return (
            <Card key={props.profile.email} className='mt-2'>
                <Card.Body>
                    <Card.Title>
                        {props.profile.first_name} {props.profile.last_name}
                    </Card.Title>
                    <Card.Text>
                        {props.profile.email}
                    </Card.Text>
                </Card.Body>
            </Card>
        )
}

export function ProductCard(props){
    return (
        <Card key={props.product.ean} className='mt-2'>
            <Card.Body>
                <Card.Title>
                    {props.product.brand}
                </Card.Title>
                <Card.Text>
                    {props.product.name}
                </Card.Text>
            </Card.Body>
            <Card.Footer className="text-muted">{props.product.ean}</Card.Footer>
        </Card>
)
}