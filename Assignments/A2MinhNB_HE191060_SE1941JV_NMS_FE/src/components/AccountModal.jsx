import { useEffect, useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

const AccountModal = ({ show, onClose, onSubmit, initialData, submitting }) => {
    const [form, setForm] = useState({
        accountName: "",
        accountEmail: "",
        accountPassword: "",
        accountRole: 2,
    });

    useEffect(() => {
        if (initialData) {
            setForm({
                accountName: initialData.accountName || "",
                accountEmail: initialData.accountEmail || "",
                accountPassword: initialData.accountPassword || "",
                accountRole: initialData.accountRole || 2,
            });
        } else {
            setForm({
                accountName: "",
                accountEmail: "",
                accountPassword: "",
                accountRole: 2,
            });
        }
    }, [initialData, show]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!form.accountName || !form.accountEmail) {
            alert("Please fill all required fields");
            return;
        }

        // password required only when creating
        if (!initialData && (!form.accountPassword || form.accountPassword.length < 6)) {
            alert("Password is required and must be at least 6 characters");
            return;
        }

        onSubmit(form);
    };

    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>
                    {initialData ? "Edit Account" : "Create Account"}
                </Modal.Title>
            </Modal.Header>

            <Form onSubmit={handleSubmit}>
                <Modal.Body>
                    <Form.Group className="mb-3">
                        <Form.Label>Account Name</Form.Label>
                        <Form.Control
                            name="accountName"
                            value={form.accountName}
                            onChange={handleChange}
                            required
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type="email"
                            name="accountEmail"
                            value={form.accountEmail}
                            onChange={handleChange}
                            required
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            name="accountPassword"
                            value={form.accountPassword}
                            onChange={handleChange}
                            required
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Role</Form.Label>
                        <Form.Select
                            name="accountRole"
                            value={form.accountRole}
                            onChange={handleChange}
                        >
                            <option value={1}>Admin</option>
                            <option value={2}>Staff</option>
                        </Form.Select>
                    </Form.Group>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={onClose}>
                        Cancel
                    </Button>
                    <Button type="submit" disabled={submitting}>
                        {submitting ? (initialData ? "Updating..." : "Creating...") : (initialData ? "Update" : "Create")}
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    );
};

export default AccountModal;
