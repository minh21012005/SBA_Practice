import { Card, Row, Col } from "react-bootstrap";

const Dashboard = () => {
    return (
        <>
            <h3>Dashboard</h3>

            <Row className="mt-3">
                <Col md={4}>
                    <Card>
                        <Card.Body>
                            <Card.Title>Total Categories</Card.Title>
                            <Card.Text>4</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={4}>
                    <Card>
                        <Card.Body>
                            <Card.Title>Total News</Card.Title>
                            <Card.Text>3</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={4}>
                    <Card>
                        <Card.Body>
                            <Card.Title>Total Users</Card.Title>
                            <Card.Text>3</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </>
    );
};

export default Dashboard;
