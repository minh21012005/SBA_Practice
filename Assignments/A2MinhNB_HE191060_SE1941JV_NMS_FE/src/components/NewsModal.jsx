import { Modal, Button, Form } from "react-bootstrap";
import { useEffect, useState } from "react";
import { getCategories } from "../services/categoryService";
import { getTags } from "../services/tagService";

const NewsModal = ({ show, onClose, onSubmit, initialData, submitting }) => {
    const [newsTitle, setNewsTitle] = useState("");
    const [headline, setHeadline] = useState("");
    const [newsContent, setNewsContent] = useState("");
    const [newsSource, setNewsSource] = useState("");
    const [newsStatus, setNewsStatus] = useState(1);
    const [categoryId, setCategoryId] = useState(null);
    const [tagIds, setTagIds] = useState([]);
    const [categories, setCategories] = useState([]);
    const [tags, setTags] = useState([]);

    useEffect(() => {
        if (initialData) {
            setNewsTitle(initialData.newsTitle || "");
            setHeadline(initialData.headline || "");
            setNewsContent(initialData.newsContent || "");
            setNewsSource(initialData.newsSource || "");
            setNewsStatus(initialData.newsStatus ?? 1);
            setCategoryId(initialData.category?.id ?? null);
            setTagIds((initialData.tags || []).map((t) => t.id));
        } else {
            setNewsTitle("");
            setHeadline("");
            setNewsContent("");
            setNewsSource("");
            setNewsStatus(1);
            setCategoryId(null);
            setTagIds([]);
        }
    }, [initialData]);

    useEffect(() => {
        (async () => {
            try {
                const c = await getCategories({ isActive: 1, size: 100 });
                setCategories(c.data || []);
            } catch (e) { }

            try {
                const t = await getTags({ size: 100 });
                setTags(t.data || []);
            } catch (e) { }
        })();
    }, []);

    const handleSubmit = () => {
        const payload = {
            newsTitle,
            headline,
            newsContent,
            newsSource,
            newsStatus: Number(newsStatus),
            categoryId: categoryId ? Number(categoryId) : null,
            tagIds: Array.from(new Set(tagIds.map((v) => Number(v))))
        };
        // basic validation
        if (!payload.newsTitle || payload.newsTitle.trim() === "") {
            alert("Title is required");
            return;
        }
        if (!payload.categoryId) {
            alert("Category is required");
            return;
        }
        onSubmit(payload);
    };

    return (
        <Modal show={show} onHide={onClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>
                    {initialData ? "Update News" : "Create News"}
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3">
                        <Form.Label>Title</Form.Label>
                        <Form.Control
                            value={newsTitle}
                            onChange={(e) => setNewsTitle(e.target.value)}
                        />
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>Headline</Form.Label>
                        <Form.Control
                            value={headline}
                            onChange={(e) => setHeadline(e.target.value)}
                        />
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>Content</Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={4}
                            value={newsContent}
                            onChange={(e) => setNewsContent(e.target.value)}
                        />
                    </Form.Group>

                    <Form.Group className="mt-3">
                        <Form.Label>Source</Form.Label>
                        <Form.Control
                            value={newsSource}
                            onChange={(e) => setNewsSource(e.target.value)}
                        />
                    </Form.Group>

                    <Form.Group className="mt-3">
                        <Form.Label>Category</Form.Label>
                        <Form.Select value={categoryId || ""} onChange={(e) => setCategoryId(e.target.value)}>
                            <option value="">-- Select category --</option>
                            {categories.map((c) => (
                                <option key={c.id} value={c.id}>{c.categoryName}</option>
                            ))}
                        </Form.Select>
                    </Form.Group>

                    <Form.Group className="mt-3">
                        <Form.Label>Tags</Form.Label>
                        <Form.Select multiple value={tagIds.map(String)} onChange={(e) => {
                            const opts = Array.from(e.target.selectedOptions).map(o => o.value);
                            setTagIds(opts);
                        }}>
                            {tags.map((t) => (
                                <option key={t.id} value={t.id}>{t.tagName}</option>
                            ))}
                        </Form.Select>
                    </Form.Group>

                    <Form.Group className="mt-3">
                        <Form.Label>Status</Form.Label>
                        <Form.Select value={newsStatus} onChange={(e) => setNewsStatus(e.target.value)}>
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
                <Button onClick={handleSubmit} disabled={submitting}>
                    {submitting ? (initialData ? "Updating..." : "Creating...") : (initialData ? "Update" : "Create")}
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default NewsModal;
