import { Nav } from "react-bootstrap";
import { NavLink } from "react-router-dom";

const Sidebar = () => {
    // Get user account from localStorage
    let userRole = null;
    try {
        const account = JSON.parse(localStorage.getItem("funews_account") || "{}");
        userRole = account.accountRole; // 1 = Admin, 2 = Staff
    } catch (e) {
        userRole = null;
    }

    const isAdmin = userRole === 1;
    const isStaff = userRole === 2;

    const linkStyle = ({ isActive }) => ({
        display: "flex",
        alignItems: "center",
        gap: "10px",
        padding: "10px 14px",
        borderRadius: "6px",
        textDecoration: "none",
        fontWeight: 500,
        color: isActive ? "#0d6efd" : "#495057",
        backgroundColor: isActive ? "#e7f1ff" : "transparent",
        borderLeft: isActive ? "4px solid #0d6efd" : "4px solid transparent",
    });

    return (
        <div
            style={{
                width: "240px",
                minHeight: "calc(100vh - 56px)",
                backgroundColor: "#f8f9fa",
                padding: "1rem",
                borderRight: "1px solid #dee2e6",
            }}
        >
            <div
                style={{
                    fontSize: "0.75rem",
                    fontWeight: 600,
                    color: "#6c757d",
                    marginBottom: "0.75rem",
                    letterSpacing: "0.05em",
                }}
            >
                {isAdmin ? "ADMIN PANEL" : isStaff ? "STAFF PANEL" : "PANEL"}
            </div>

            <Nav className="flex-column gap-1">
                <Nav.Link as={NavLink} to="/admin/dashboard" style={linkStyle}>
                    <i className="bi bi-speedometer2" />
                    Dashboard
                </Nav.Link>

                {/* Admin only: Users Management */}
                {isAdmin && (
                    <Nav.Link as={NavLink} to="/admin/users" style={linkStyle}>
                        <i className="bi bi-people" />
                        Users
                    </Nav.Link>
                )}

                {/* Staff: Categories, News, Tags, Profile, My News */}
                {isStaff && (
                    <>
                        <Nav.Link as={NavLink} to="/admin/categories" style={linkStyle}>
                            <i className="bi bi-folder" />
                            Categories
                        </Nav.Link>

                        <Nav.Link as={NavLink} to="/admin/news" style={linkStyle}>
                            <i className="bi bi-newspaper" />
                            News
                        </Nav.Link>

                        <Nav.Link as={NavLink} to="/admin/tags" style={linkStyle}>
                            <i className="bi bi-tags" />
                            Tags
                        </Nav.Link>

                        <Nav.Link as={NavLink} to="/admin/my-news" style={linkStyle}>
                            <i className="bi bi-clock-history" />
                            My News
                        </Nav.Link>
                    </>
                )}

                {/* Profile: Both Admin and Staff */}
                <Nav.Link as={NavLink} to="/admin/settings" style={linkStyle}>
                    <i className="bi bi-gear" />
                    Profile
                </Nav.Link>
            </Nav>
        </div>
    );
};

export default Sidebar;
