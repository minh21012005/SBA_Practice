import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Card, Form, Button, InputGroup, Alert } from "react-bootstrap";
import { Eye, EyeSlash } from "react-bootstrap-icons";

const Login = () => {
    const navigate = useNavigate();

    const [form, setForm] = useState({ username: "", password: "" });
    const [showPassword, setShowPassword] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            setError("");
            // call backend login
            const { login } = await import("../services/authService");
            const res = await login(form.username, form.password);
            if (res?.data?.token) {
                navigate("/admin/dashboard");
            } else {
                setError("Tên đăng nhập hoặc mật khẩu không đúng");
            }
        } catch (err) {
            setError("Tên đăng nhập hoặc mật khẩu không đúng");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="d-flex align-items-center justify-content-center min-vh-100 bg-light">
            <Container style={{ maxWidth: 420 }}>
                <Card className="shadow-sm border-0">
                    <Card.Body className="p-4 p-md-5">
                        {/* Header */}
                        <div className="text-center mb-4">
                            <h4 className="fw-semibold mb-1">FUNews</h4>
                            <div className="text-muted small">
                                Hệ thống quản lý tin tức
                            </div>
                        </div>

                        {/* Error */}
                        {error && (
                            <Alert variant="danger" className="py-2 text-center">
                                {error}
                            </Alert>
                        )}

                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3">
                                <Form.Label className="fw-medium">
                                    Tên đăng nhập
                                </Form.Label>
                                <Form.Control
                                    name="username"
                                    placeholder="Nhập tên đăng nhập"
                                    value={form.username}
                                    onChange={handleChange}
                                    autoFocus
                                />
                            </Form.Group>

                            <Form.Group className="mb-4">
                                <Form.Label className="fw-medium">
                                    Mật khẩu
                                </Form.Label>
                                <InputGroup>
                                    <Form.Control
                                        name="password"
                                        type={showPassword ? "text" : "password"}
                                        placeholder="Nhập mật khẩu"
                                        value={form.password}
                                        onChange={handleChange}
                                    />
                                    <Button
                                        variant="outline-secondary"
                                        onClick={() => setShowPassword((v) => !v)}
                                        tabIndex={-1}
                                    >
                                        {showPassword ? <EyeSlash /> : <Eye />}
                                    </Button>
                                </InputGroup>
                            </Form.Group>

                            <Button
                                type="submit"
                                className="w-100"
                                disabled={loading}
                            >
                                {loading ? "Đang đăng nhập..." : "Đăng nhập"}
                            </Button>
                        </Form>

                        <div className="text-center text-muted small mt-4">
                            © {new Date().getFullYear()} FUNews Management System
                        </div>
                    </Card.Body>
                </Card>
            </Container>
        </div>
    );
};

export default Login;
