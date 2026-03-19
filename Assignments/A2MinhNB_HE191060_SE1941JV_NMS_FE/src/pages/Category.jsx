import { useEffect, useState } from "react";
import { Button, Table, Form, Badge } from "react-bootstrap";
import {
    getCategories,
    createCategory,
    updateCategory,
    deleteCategory,
} from "../services/categoryService";
import CategoryModal from "../components/CategoryModal";
import ConfirmDialog from "../components/ConfirmDialog";

const Category = () => {
    const [categories, setCategories] = useState([]);
    const [search, setSearch] = useState("");
    const [selected, setSelected] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [showConfirm, setShowConfirm] = useState(false);

    const fetchCategories = async () => {
        try {
            const res = await getCategories();
            setCategories(res.data || []);
            setError("");
        } catch (err) {
            setError("Không thể tải danh sách chuyên mục");
        }
    };

    useEffect(() => {
        fetchCategories();
    }, []);

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const filteredCategories = categories.filter((c) =>
        c.categoryName.toLowerCase().includes(search.toLowerCase())
    );

    const handleSave = async (data) => {
        setLoading(true);
        setError("");
        setSuccess("");
        try {
            if (!data.categoryName) throw new Error("Category name required");
            if (selected) {
                await updateCategory(selected.id, data);
                setSuccess("Cập nhật chuyên mục thành công");
            } else {
                await createCategory(data);
                setSuccess("Tạo chuyên mục thành công");
            }
            setShowModal(false);
            await fetchCategories();
        } catch (err) {
            setError(err?.response?.data?.message || err.message || "Thao tác thất bại");
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async () => {
        setLoading(true);
        setError("");
        try {
            await deleteCategory(selected.id);
            setShowConfirm(false);
            setSuccess("Xoá chuyên mục thành công");
            await fetchCategories();
        } catch (err) {
            setError(err?.response?.data?.message || "Không thể xóa danh mục này");
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <div className="d-flex justify-content-between mb-3">
                <Form.Control
                    placeholder="Search category..."
                    style={{ width: 300 }}
                    onChange={(e) => setSearch(e.target.value)}
                />
                <Button
                    onClick={() => {
                        setSelected(null);
                        setShowModal(true);
                    }}
                >
                    Create Category
                </Button>
            </div>

            <Table bordered hover>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th width="160">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredCategories.map((c) => (
                        <tr key={c.id}>
                            <td>{c.categoryName}</td>
                            <td>{c.categoryDesciption}</td>
                            <td>
                                {c.isActive ? (
                                    <Badge bg="success">Active</Badge>
                                ) : (
                                    <Badge bg="secondary">Inactive</Badge>
                                )}
                            </td>
                            <td>
                                <Button
                                    size="sm"
                                    variant="outline-primary"
                                    className="me-2"
                                    onClick={() => {
                                        setSelected(c);
                                        setShowModal(true);
                                    }}
                                >
                                    Edit
                                </Button>
                                <Button
                                    size="sm"
                                    variant="outline-danger"
                                    onClick={() => {
                                        setSelected(c);
                                        setShowConfirm(true);
                                    }}
                                >
                                    Delete
                                </Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>

            <CategoryModal
                show={showModal}
                onClose={() => setShowModal(false)}
                onSubmit={handleSave}
                initialData={selected}
                submitting={loading}
            />

            <ConfirmDialog
                show={showConfirm}
                onClose={() => setShowConfirm(false)}
                onConfirm={handleDelete}
                title="Delete Category"
                message="Are you sure you want to delete this category?"
            />

            {error && <div className="mt-2"><div className="alert alert-danger">{error}</div></div>}
            {success && <div className="mt-2"><div className="alert alert-success">{success}</div></div>}
        </>
    );
};

export default Category;
