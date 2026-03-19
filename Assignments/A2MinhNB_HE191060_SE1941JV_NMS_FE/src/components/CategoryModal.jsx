import { useEffect, useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

const CategoryModal = ({ show, onClose, onSubmit, initialData, submitting }) => {
    const [form, setForm] = useState({
        categoryName: "",
        categoryDesciption: "",
        isActive: true,
    });

    useEffect(() => {
        if (initialData) {
            setForm({
                categoryName: initialData.categoryName,
                categoryDesciption: initialData.categoryDesciption,
                isActive: initialData.isActive,
            });
        } else {
            setForm({
                categoryName: "",
                categoryDesciption: "",
                isActive: true,
            });
        }
    }, [initialData]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({
            ...form,
            [name]: name === "isActive" ? (value === "true" || value === true || value === 1 || value === "1") : value,
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
                            name="categoryDesciption"
                            value={form.categoryDesciption}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>Status</Form.Label>
                        <Form.Select
                            name="isActive"
                            value={String(form.isActive)}
                            onChange={handleChange}
                        >
                            <option value="true">Active</option>
                            <option value="false">Inactive</option>
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
