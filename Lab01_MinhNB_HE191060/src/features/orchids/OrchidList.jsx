import { Button, Card, Col, Container, Row } from "react-bootstrap";

const OrchidList = () => {

    const orchidList = [
        { id: 1, name: 'Phalaenopsis', link: 'https://cellphones.com.vn/sforum/wp-content/uploads/2024/01/hinh-nen-hoa-hong-1.jpg' },
        { id: 2, name: 'Cattleya', link: 'https://dienhoavip.vn/wp-content/uploads/2024/11/hinh-anh-hoa-dep-672eeb979cd04.webp' },
        { id: 3, name: 'Dendrobium', link: 'https://bigpicturesb.org/wp-content/uploads/2024/10/hinh-nen-anh-hoa-dep-tu-nhien-48.jpeg' },
        { id: 4, name: 'Vanda', link: 'https://cdn11.dienmaycholon.vn/filewebdmclnew/public/userupload/files/Image%20FP_2024/anh-hoa-tulip-3.jpg' },
        { id: 5, name: 'Oncidium', link: 'https://marketplace.canva.com/sdcNU/MAEE4osdcNU/1/tl/canva-beautiful-flowers-background-MAEE4osdcNU.jpg' },
        { id: 6, name: 'Paphiopedilum', link: 'https://png.pngtree.com/thumb_back/fh260/background/20230804/pngtree-flowers-of-cosmos-beautiful-and-beautiful-purple-flowers-spring-green-field-image_13001920.jpg' },
        { id: 7, name: 'Miltonia', link: 'https://heucollege.edu.vn/upload/2025/03/hoa-cuc-hoa-mi03.webp' },
        { id: 8, name: 'Cymbidium', link: 'https://img.pikbest.com/backgrounds/20250126/high-quality-red-rose-image-for-nature-lovers_11454376.jpg!w700wp' },
    ];

    return (
        <Container>
            <Row className="p-3">
                {orchidList.map((orchid) => (
                    <Col
                        key={orchid.id}
                        xs={12}
                        sm={6}
                        md={3}
                        className="mb-4 d-flex"
                    >
                        <Card className="h-100 w-100 shadow-sm">
                            <Card.Img
                                variant="top"
                                src={orchid.link}
                                style={{
                                    height: "380px",
                                    objectFit: "cover"
                                }}
                            />

                            <Card.Body className="d-flex flex-column">
                                <Card.Title>
                                    {orchid.name}
                                </Card.Title>

                                <Button
                                    variant="primary"
                                    size="sm"
                                    className="mt-auto align-self-start"
                                >
                                    Detail
                                </Button>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
        </Container>
    );
};

export default OrchidList;
