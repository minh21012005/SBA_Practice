import { useEffect, useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

const TagModal = ({ show, onClose, onSubmit, initialData, submitting }) => {
    const [form, setForm] = useState({ tagName: "", note: "" });

    useEffect(() => {
        if (initialData) {
            setForm({ tagName: initialData.tagName || "", note: initialData.note || "" });
        } else {
            setForm({ tagName: "", note: "" });
        }
    }, [initialData, show]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((p) => ({ ...p, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!form.tagName || form.tagName.trim() === "") {
            alert("Tag name is required");
            return;
        }
        onSubmit(form);
    };

    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>{initialData ? "Edit Tag" : "Create Tag"}</Modal.Title>
            </Modal.Header>

            <Form onSubmit={handleSubmit}>
                <Modal.Body>
                    <Form.Group className="mb-3">
                        <Form.Label>Tag Name</Form.Label>
                        <Form.Control name="tagName" value={form.tagName} onChange={handleChange} required />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Note</Form.Label>
                        <Form.Control name="note" value={form.note} onChange={handleChange} />
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

export default TagModal;
