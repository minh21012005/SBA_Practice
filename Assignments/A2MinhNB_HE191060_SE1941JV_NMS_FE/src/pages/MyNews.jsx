import { useEffect, useState } from "react";
import { Button, Table, Form, Badge } from "react-bootstrap";
import { getMyNews } from "../services/newsService";

const MyNews = () => {
    const [news, setNews] = useState([]);
    const [filteredNews, setFilteredNews] = useState([]);
    const [search, setSearch] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const fetchMyNews = async () => {
        setLoading(true);
        try {
            // Fetch news created by current user via /api/my/news
            const res = await getMyNews();
            const data = res.data || [];
            setNews(data);
            setFilteredNews(data);
            setError("");
        } catch (err) {
            setError("Không thể tải danh sách tin tức của bạn");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchMyNews();
    }, []);

    useEffect(() => {
        const keyword = search.toLowerCase();
        const filtered = news.filter((n) =>
            n.newsTitle.toLowerCase().includes(keyword) ||
            n.headline.toLowerCase().includes(keyword)
        );
        setFilteredNews(filtered);
    }, [search, news]);

    return (
        <>
            <h3>My News</h3>

            <div className="d-flex justify-content-between mb-3">
                <Form.Control
                    placeholder="Search my news..."
                    style={{ width: 300 }}
                    onChange={(e) => setSearch(e.target.value)}
                />
                <Button onClick={fetchMyNews} disabled={loading}>
                    {loading ? "Loading..." : "Refresh"}
                </Button>
            </div>

            {error && <div className="mb-2"><div className="alert alert-danger">{error}</div></div>}

            {filteredNews.length === 0 ? (
                <div className="alert alert-info">No articles created by you yet.</div>
            ) : (
                <Table bordered hover>
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Headline</th>
                            <th>Category</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredNews.map((n) => (
                            <tr key={n.id}>
                                <td>{n.newsTitle}</td>
                                <td>{n.headline}</td>
                                <td>{n.category?.categoryName || "N/A"}</td>
                                <td>
                                    {n.newsStatus === 1 ? (
                                        <Badge bg="success">Active</Badge>
                                    ) : (
                                        <Badge bg="secondary">Inactive</Badge>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            )}
        </>
    );
};

export default MyNews;
