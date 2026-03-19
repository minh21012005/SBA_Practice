import { useEffect, useState } from "react";
import { Card, Row, Col, Spinner, Alert } from "react-bootstrap";
import { Navigate } from "react-router-dom";
import axiosClient from "../services/axiosClient";

const Dashboard = () => {
    // Staff should not access dashboard
    const account = JSON.parse(localStorage.getItem("funews_account") || "{}");
    if (account.accountRole !== 1) {
        return <Navigate to="/admin/my-news" />;
    }
    const [stats, setStats] = useState({
        categories: 0,
        news: 0,
        users: 0
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchStats = async () => {
            setLoading(true);
            setError("");
            try {
                const results = await Promise.allSettled([
                    axiosClient.get("/categories", { params: { size: 1 } }),
                    axiosClient.get("/news", { params: { size: 1 } }),
                    axiosClient.get("/accounts", { params: { size: 1 } })
                ]);

                const [catRes, newsRes, userRes] = results;

                setStats({
                    categories: catRes.status === "fulfilled" ? (catRes.value.data?.totalElements || 0) : "Hidden",
                    news: newsRes.status === "fulfilled" ? (newsRes.value.data?.totalElements || 0) : "Hidden",
                    users: userRes.status === "fulfilled" ? (userRes.value.data?.totalElements || 0) : "Hidden"
                });

                // If all failed, show a general error
                if (results.every(r => r.status === "rejected")) {
                    setError("Could not load any dashboard statistics. Please ensure the Backend is running.");
                }
            } catch (err) {
                console.error("Dashboard fetch error:", err);
                setError("Something went wrong while loading statistics.");
            } finally {
                setLoading(false);
            }
        };

        fetchStats();
    }, []);

    if (loading) return (
        <div className="text-center mt-5">
            <Spinner animation="border" />
            <p className="mt-2">Loading statistics...</p>
        </div>
    );

    return (
        <>
            <h3>Dashboard</h3>

            {error && <Alert variant="danger" className="mt-3">{error}</Alert>}

            <Row className="mt-3">
                <Col md={4}>
                    <Card className="text-center shadow-sm">
                        <Card.Body>
                            <Card.Title className="text-muted">Total Categories</Card.Title>
                            <Card.Text className="display-4 font-weight-bold">{stats.categories}</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={4}>
                    <Card className="text-center shadow-sm">
                        <Card.Body>
                            <Card.Title className="text-muted">Total News</Card.Title>
                            <Card.Text className="display-4 font-weight-bold text-primary">{stats.news}</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={4}>
                    <Card className="text-center shadow-sm">
                        <Card.Body>
                            <Card.Title className="text-muted">Total Users</Card.Title>
                            <Card.Text className="display-4 font-weight-bold text-success">{stats.users}</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </>
    );
};

export default Dashboard;
