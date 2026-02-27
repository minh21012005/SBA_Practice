import { useEffect, useState } from "react";
import { Button, Table, Form } from "react-bootstrap";
import { createNews, deleteNews, getNews, updateNews } from "../services/newsService";
import NewsModal from "../components/NewsModal";
import ConfirmDialog from "../components/ConfirmDialog";

const News = () => {
    const [news, setNews] = useState([]);
    const [filteredNews, setFilteredNews] = useState([]);
    const [search, setSearch] = useState("");
    const [selected, setSelected] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [showConfirm, setShowConfirm] = useState(false);

    const fetchNews = async () => {
        try {
            const res = await getNews();
            const data = res.data || [];
            setNews(data);
            setFilteredNews(data);
            setError("");
        } catch (err) {
            setError("Không thể tải danh sách tin tức");
        }
    };

    useEffect(() => {
        fetchNews();
    }, []);

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    useEffect(() => {
        const keyword = search.toLowerCase();

        const filtered = news.filter((n) =>
            n.newsTitle.toLowerCase().includes(keyword) ||
            n.headline.toLowerCase().includes(keyword) ||
            n.newsContent.toLowerCase().includes(keyword)
        );

        setFilteredNews(filtered);
    }, [search, news]);

    const handleSave = async (data) => {
        setLoading(true);
        setError("");
        setSuccess("");
        try {
            if (!data.newsTitle) throw new Error("Title required");
            if (data.categoryId == null) throw new Error("Category required");

            if (selected) {
                await updateNews(selected.id, data);
                setSuccess("Cập nhật tin tức thành công");
            } else {
                await createNews(data);
                setSuccess("Tạo tin tức thành công");
            }
            setShowModal(false);
            await fetchNews();
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
            await deleteNews(selected.id);
            setShowConfirm(false);
            setSuccess("Xoá tin tức thành công");
            await fetchNews();
        } catch (err) {
            setError(err?.response?.data?.message || "Không thể xóa");
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <div className="d-flex justify-content-between mb-3">
                <Form.Control
                    placeholder="Search news..."
                    style={{ width: 300 }}
                    onChange={(e) => setSearch(e.target.value)}
                />
                <Button onClick={() => {
                    setSelected(null);
                    setShowModal(true);
                }}>
                    Create News
                </Button>
            </div>

            {error && <div className="mb-2"><div className="alert alert-danger">{error}</div></div>}
            {success && <div className="mb-2"><div className="alert alert-success">{success}</div></div>}

            <Table bordered hover>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Headline</th>
                        <th>Content</th>
                        <th width="160">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredNews.map((n) => (
                        <tr key={n.id}>
                            <td>{n.newsTitle}</td>
                            <td>{n.headline}</td>
                            <td>{n.newsContent}</td>
                            <td>
                                <Button
                                    size="sm"
                                    variant="outline-primary"
                                    className="me-2"
                                    onClick={() => {
                                        setSelected(n);
                                        setShowModal(true);
                                    }}
                                >
                                    Edit
                                </Button>
                                <Button
                                    size="sm"
                                    variant="outline-danger"
                                    onClick={() => {
                                        setSelected(n);
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

            <NewsModal
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
                title="Delete News"
                message="Are you sure you want to delete this news?"
            />
        </>
    );
};

export default News;
