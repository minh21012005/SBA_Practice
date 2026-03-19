import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Container, Card, Form, Button, InputGroup, Alert } from "react-bootstrap";
import { Eye, EyeSlash } from "react-bootstrap-icons";
import { register } from "../services/authService";

const Register = () => {
    const navigate = useNavigate();

    const [form, setForm] = useState({ name: "", email: "", password: "" });
    const [showPassword, setShowPassword] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            await register(form.name, form.email, form.password);
            setSuccess(true);
            setTimeout(() => {
                navigate("/login");
            }, 2000);
        } catch (err) {
            console.error("Register error:", err);
            setError(err.response?.data?.message || "Đăng ký thất bại. Vui lòng thử lại.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="d-flex align-items-center justify-content-center min-vh-100 bg-light">
            <Container style={{ maxWidth: 420 }}>
                <Card className="shadow-sm border-0">
                    <Card.Body className="p-4 p-md-5">
                        <div className="text-center mb-4">
                            <h4 className="fw-semibold mb-1">FUNews</h4>
                            <div className="text-muted small">
                                Đăng ký tài khoản mới (Staff)
                            </div>
                        </div>

                        {error && (
                            <Alert variant="danger" className="py-2 text-center text-small">
                                {error}
                            </Alert>
                        )}

                        {success && (
                            <Alert variant="success" className="py-2 text-center text-small">
                                Đăng ký thành công! Đang chuyển hướng...
                            </Alert>
                        )}

                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3">
                                <Form.Label className="fw-medium small">Họ và tên</Form.Label>
                                <Form.Control
                                    name="name"
                                    placeholder="Nhập họ và tên"
                                    value={form.name}
                                    onChange={handleChange}
                                    required
                                    autoFocus
                                />
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label className="fw-medium small">Email</Form.Label>
                                <Form.Control
                                    name="email"
                                    type="email"
                                    placeholder="Nhập email"
                                    value={form.email}
                                    onChange={handleChange}
                                    required
                                />
                            </Form.Group>

                            <Form.Group className="mb-4">
                                <Form.Label className="fw-medium small">Mật khẩu</Form.Label>
                                <InputGroup>
                                    <Form.Control
                                        name="password"
                                        type={showPassword ? "text" : "password"}
                                        placeholder="Nhập mật khẩu (tối thiểu 6 ký tự)"
                                        value={form.password}
                                        onChange={handleChange}
                                        required
                                        minLength={6}
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
                                className="w-100 py-2 mb-3"
                                disabled={loading || success}
                            >
                                {loading ? "Đang xử lý..." : "Đăng ký"}
                            </Button>

                            <div className="text-center small">
                                Đã có tài khoản? <Link to="/login" className="text-decoration-none">Đăng nhập ngay</Link>
                            </div>
                        </Form>

                        <div className="text-center text-muted x-small mt-4">
                            © {new Date().getFullYear()} FUNews Management System
                        </div>
                    </Card.Body>
                </Card>
            </Container>
        </div>
    );
};

export default Register;
