import { useEffect, useState } from "react";
import { Button, Table, Form } from "react-bootstrap";
import { getTags, createTag, updateTag, deleteTag } from "../services/tagService";
import TagModal from "../components/TagModal";
import ConfirmDialog from "../components/ConfirmDialog";

const Tags = () => {
    const [tags, setTags] = useState([]);
    const [search, setSearch] = useState("");
    const [selected, setSelected] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [showConfirm, setShowConfirm] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const fetchTags = async () => {
        try {
            const res = await getTags({ size: 100 });
            setTags(res.data || []);
            setError("");
        } catch (err) {
            setError("Không thể tải danh sách tag");
        }
    };

    useEffect(() => {
        fetchTags();
    }, []);

    const filtered = tags.filter((t) => t.tagName.toLowerCase().includes(search.toLowerCase()));

    const handleSave = async (data) => {
        setLoading(true);
        setError("");
        setSuccess("");
        try {
            if (selected) {
                await updateTag(selected.id, data);
                setSuccess("Cập nhật tag thành công");
            } else {
                await createTag(data);
                setSuccess("Tạo tag thành công");
            }
            setShowModal(false);
            await fetchTags();
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
            await deleteTag(selected.id);
            setShowConfirm(false);
            setSuccess("Xoá tag thành công");
            await fetchTags();
        } catch (err) {
            setError(err?.response?.data?.message || "Không thể xóa tag");
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <div className="d-flex justify-content-between mb-3">
                <Form.Control placeholder="Search tag..." style={{ width: 300 }} onChange={(e) => setSearch(e.target.value)} />
                <Button onClick={() => { setSelected(null); setShowModal(true); }}>Create Tag</Button>
            </div>

            {error && <div className="mb-2"><div className="alert alert-danger">{error}</div></div>}
            {success && <div className="mb-2"><div className="alert alert-success">{success}</div></div>}

            <Table bordered hover>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Note</th>
                        <th width="160">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {filtered.map((t) => (
                        <tr key={t.id}>
                            <td>{t.tagName}</td>
                            <td>{t.note}</td>
                            <td>
                                <Button size="sm" variant="outline-primary" className="me-2" onClick={() => { setSelected(t); setShowModal(true); }}>Edit</Button>
                                <Button size="sm" variant="outline-danger" onClick={() => { setSelected(t); setShowConfirm(true); }}>Delete</Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>

            <TagModal show={showModal} onClose={() => setShowModal(false)} onSubmit={handleSave} initialData={selected} submitting={loading} />

            <ConfirmDialog show={showConfirm} onClose={() => setShowConfirm(false)} onConfirm={handleDelete} title="Delete Tag" message="Are you sure you want to delete this tag?" />
        </>
    );
};

export default Tags;
