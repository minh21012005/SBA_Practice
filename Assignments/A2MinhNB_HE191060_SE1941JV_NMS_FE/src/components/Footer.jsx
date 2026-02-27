import { Container } from "react-bootstrap";

const Footer = () => {
    return (
        <footer
            style={{
                backgroundColor: "#f1f3f5",
                padding: "10px 0",
                textAlign: "center",
                borderTop: "1px solid #dee2e6",
            }}
        >
            <Container>
                © 2026 FUNews Management System
            </Container>
        </footer>
    );
};

export default Footer;
