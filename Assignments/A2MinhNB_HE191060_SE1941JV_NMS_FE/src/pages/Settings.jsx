import { useEffect, useState } from "react";
import { Card, Form, Button, Alert } from "react-bootstrap";
import { getProfile, updateProfile } from "../services/accountService";

const Settings = () => {
    const [form, setForm] = useState({ accountName: "", accountEmail: "", accountPassword: "" });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    useEffect(() => {
        (async () => {
            try {
                const res = await getProfile();
                const data = res.data || {};
                setForm({ accountName: data.accountName || "", accountEmail: data.accountEmail || "", accountPassword: "" });
            } catch (e) {
                setError("Không thể tải thông tin hồ sơ");
            }
        })();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((p) => ({ ...p, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess("");
        setLoading(true);
        try {
            const payload = {
                accountName: form.accountName,
                accountEmail: form.accountEmail,
            };
            if (form.accountPassword) payload.accountPassword = form.accountPassword;
            await updateProfile(payload);
            setSuccess("Cập nhật hồ sơ thành công");
            setForm((p) => ({ ...p, accountPassword: "" }));
        } catch (err) {
            setError("Cập nhật thất bại");
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <h3>Profile</h3>

            <Card className="mt-3">
                <Card.Body>
                    {error && <Alert variant="danger">{error}</Alert>}
                    {success && <Alert variant="success">{success}</Alert>}

                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3">
                            <Form.Label>Name</Form.Label>
                            <Form.Control name="accountName" value={form.accountName} onChange={handleChange} required />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Email</Form.Label>
                            <Form.Control type="email" name="accountEmail" value={form.accountEmail} onChange={handleChange} required />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>New Password (leave empty to keep current)</Form.Label>
                            <Form.Control type="password" name="accountPassword" value={form.accountPassword} onChange={handleChange} />
                        </Form.Group>

                        <Button type="submit" disabled={loading}>{loading ? 'Saving...' : 'Save'}</Button>
                    </Form>
                </Card.Body>
            </Card>
        </>
    );
};

export default Settings;
