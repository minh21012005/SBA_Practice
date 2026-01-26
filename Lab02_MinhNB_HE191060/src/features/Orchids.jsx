import React, { useState } from "react";
import { Row, Col, Container, Card, Button, Modal } from "react-bootstrap";
import { OrchidsData } from "../shared/data/ListOfOrchids.js"

export default function Orchids() {
    const [show, setShow] = useState(false);
    const [selectedOrchid, setSelectedOrchid] = useState(null);

    const handleClose = () => setShow(false);

    const handleShow = (orchid) => {
        setSelectedOrchid(orchid);
        setShow(true);
    };

    return (
        <Container>
            <Row>
                {OrchidsData.map((orchid) => (
                    <Col md={3} key={orchid.id} className="mb-4">
                        <Card>
                            <Card.Img variant="top" src={orchid.image} style={{ height: 380 }} />
                            <Card.Body>
                                <Card.Title>{orchid.orchidName}</Card.Title>
                                <Card.Text>{orchid.category}</Card.Text>
                                <Button
                                    variant="primary"
                                    onClick={() => handleShow(orchid)}
                                >
                                    Detail
                                </Button>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            <Modal show={show} onHide={handleClose} centered>
                <Modal.Header closeButton>
                    <Modal.Title>
                        {selectedOrchid ? selectedOrchid.orchidName : ""}
                    </Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    {selectedOrchid ? (
                        <div>
                            <img
                                src={selectedOrchid.image}
                                alt={selectedOrchid.orchidName}
                                style={{ width: "100%", height: "360px" }}
                            />
                            <p className="mt-3">{selectedOrchid.description}</p>
                        </div>
                    ) : (
                        <p>Loading details...</p>
                    )}
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
}
