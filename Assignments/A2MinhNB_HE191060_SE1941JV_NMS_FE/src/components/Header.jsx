import { Navbar, Container, Nav, NavDropdown, Image, Button } from "react-bootstrap";
import logo from "../assets/logo.png";
import { useNavigate } from "react-router-dom";

const Header = () => {

    const nav = useNavigate();
    let user = null;
    try {
        user = JSON.parse(localStorage.getItem("funews_account")) || null;
    } catch (e) {
        user = null;
    }

    const handleLogout = () => {
        localStorage.removeItem("funews_token");
        localStorage.removeItem("funews_account");
        nav("/login");
    };

    return (
        <Navbar
            bg="light"
            expand="lg"
            className="shadow-sm px-3"
            style={{ borderBottom: "1px solid #e5e7eb" }}
        >
            <Container fluid>
                {/* Left: Logo + Brand */}
                <Navbar.Brand className="d-flex align-items-center gap-2 fw-semibold text-dark">
                    <img
                        src={logo}
                        alt="Logo"
                        style={{ height: "40px", objectFit: "contain" }}
                    />
                    FUNews Management System
                </Navbar.Brand>

                <Navbar.Toggle />

                {/* Right: User info */}
                <Navbar.Collapse className="justify-content-end">
                    <Nav className="align-items-center gap-3">
                        <div className="text-end d-none d-md-block">
                            <div className="fw-semibold text-dark">{user ? user.accountName : "Guest"}</div>
                            <div className="text-muted" style={{ fontSize: "0.85rem" }}>
                                {user ? (user.accountRole === 1 ? "Admin" : "Staff") : ""}
                            </div>
                        </div>

                        <NavDropdown
                            align="end"
                            title={
                                <Image
                                    src={user?.avatar || "https://i.pravatar.cc/40"}
                                    roundedCircle
                                    width={40}
                                    height={40}
                                />
                            }
                            id="user-dropdown"
                        >
                            <NavDropdown.Item onClick={() => nav('/admin/settings')}>Profile</NavDropdown.Item>
                            <NavDropdown.Item onClick={() => nav('/admin/settings')}>Settings</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item
                                onClick={handleLogout}
                                className="text-danger"
                            >
                                Logout
                            </NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;
