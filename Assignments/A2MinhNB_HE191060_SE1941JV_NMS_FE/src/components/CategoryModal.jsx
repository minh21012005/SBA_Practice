import { useEffect, useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

const CategoryModal = ({ show, onClose, onSubmit, initialData, submitting }) => {
    const [form, setForm] = useState({
        categoryName: "",
        categoryDescription: "",
        isActive: 1,
    });

    useEffect(() => {
        if (initialData) {
            setForm({
                categoryName: initialData.categoryName,
                categoryDescription: initialData.categoryDescription,
                isActive: initialData.isActive,
            });
        } else {
            setForm({
                categoryName: "",
                categoryDescription: "",
                isActive: 1,
            });
        }
    }, [initialData]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({
            ...form,
            [name]: name === "isActive" ? Number(value) : value,
        });
    };

    const handleSubmit = () => {
        if (!form.categoryName || form.categoryName.trim() === "") {
            alert("Category name is required");
            return;
        }
        onSubmit(form);
    };

    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>
                    {initialData ? "Edit Category" : "Create Category"}
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3">
                        <Form.Label>Category Name</Form.Label>
                        <Form.Control
                            name="categoryName"
                            value={form.categoryName}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            name="categoryDescription"
                            value={form.categoryDescription}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>Status</Form.Label>
                        <Form.Select
                            name="isActive"
                            value={form.isActive}
                            onChange={handleChange}
                        >
                            <option value={1}>Active</option>
                            <option value={0}>Inactive</option>
                        </Form.Select>
                    </Form.Group>
                </Form>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={onClose}>
                    Cancel
                </Button>
                <Button variant="primary" onClick={handleSubmit} disabled={submitting}>
                    {submitting ? "Saving..." : "Save"}
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default CategoryModal;
