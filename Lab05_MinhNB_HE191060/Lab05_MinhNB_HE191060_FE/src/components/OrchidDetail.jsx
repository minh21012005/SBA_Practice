import { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { getOrchidById, deleteOrchid } from '../services/orchidService';

const OrchidDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [orchid, setOrchid] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOrchid = async () => {
            try {
                setLoading(true);
                const response = await getOrchidById(id);
                setOrchid(response.data);
                setError(null);
            } catch (err) {
                console.error("Error fetching orchid:", err);
                setError("Failed to load orchid details. It might have been deleted.");
            } finally {
                setLoading(false);
            }
        };

        fetchOrchid();
    }, [id]);

    const handleDelete = async () => {
        if (window.confirm("Are you sure you want to delete this orchid?")) {
            try {
                await deleteOrchid(id);
                alert("Orchid deleted successfully!");
                navigate('/orchids');
            } catch (err) {
                console.error("Error deleting orchid:", err);
                alert("Failed to delete the orchid. Please try again.");
            }
        }
    };

    if (loading) return <div className="text-center mt-5"><div className="spinner-border text-primary" role="status"></div></div>;
    if (error) return <div className="alert alert-danger mt-3 m-4" role="alert">{error}</div>;
    if (!orchid) return <div className="alert alert-warning mt-3 m-4" role="alert">Orchid not found</div>;

    return (
        <div className="container mt-4 mb-5">
            <div className="card shadow-lg mx-auto" style={{ maxWidth: '900px', borderRadius: '15px', overflow: 'hidden' }}>
                <div className="row g-0">
                    <div className="col-md-5 bg-light d-flex align-items-center justify-content-center p-4">
                        {orchid.orchidUrl ? (
                            <img
                                src={orchid.orchidUrl}
                                alt={orchid.orchidName}
                                className="img-fluid rounded shadow-sm"
                                style={{ maxHeight: '400px', objectFit: 'cover' }}
                                onError={(e) => {
                                    e.target.onerror = null;
                                    e.target.src = "https://via.placeholder.com/400x400?text=No+Image+Available";
                                }}
                            />
                        ) : (
                            <div className="text-center text-muted">
                                <i className="bi bi-image" style={{ fontSize: '4rem' }}></i>
                                <p className="mt-2">No Image Available</p>
                            </div>
                        )}
                    </div>
                    <div className="col-md-7">
                        <div className="card-body p-5">
                            <div className="d-flex justify-content-between align-items-start mb-3">
                                <h2 className="card-title fw-bold text-primary mb-0">{orchid.orchidName}</h2>
                                <span className="badge bg-secondary fs-6 px-3 py-2 rounded-pill">{orchid.orchidCategory}</span>
                            </div>

                            <hr className="my-4" />

                            <div className="mb-4">
                                <h5 className="text-muted fw-bold mb-2">Description</h5>
                                <p className="card-text fs-5" style={{ lineHeight: '1.6' }}>
                                    {orchid.orchidDescription || <span className="text-secondary fst-italic">No description provided.</span>}
                                </p>
                            </div>

                            <div className="row mb-5 bg-light p-3 rounded mx-0">
                                <div className="col-6 mb-2">
                                    <div className="fw-bold text-muted mb-1"><small>ID</small></div>
                                    <div className="fs-5">{orchid.orchidId}</div>
                                </div>
                                <div className="col-6 mb-2">
                                    <div className="fw-bold text-muted mb-1"><small>Status</small></div>
                                    <div className="fs-5">
                                        {orchid.natural ? (
                                            <span className="badge bg-success me-2">Natural <i className="bi bi-tree-fill"></i></span>
                                        ) : (
                                            <span className="badge bg-secondary me-2">Cultivated</span>
                                        )}
                                        {orchid.attractive && (
                                            <span className="badge bg-danger"><i className="bi bi-star-fill"></i> Attractive</span>
                                        )}
                                    </div>
                                </div>
                            </div>

                            <div className="d-flex justify-content-between align-items-center mb-0 mt-auto">
                                <Link to="/orchids" className="btn btn-outline-secondary px-4 py-2 fw-semibold">
                                    <i className="bi bi-arrow-left me-2"></i>Back to List
                                </Link>
                                <div className="gap-2 d-flex">
                                    <Link to={`/orchids/edit/${orchid.orchidId}`} className="btn btn-warning px-4 py-2 fw-semibold text-dark shadow-sm">
                                        <i className="bi bi-pencil-square me-2"></i>Edit
                                    </Link>
                                    <button onClick={handleDelete} className="btn btn-danger px-4 py-2 fw-semibold shadow-sm">
                                        <i className="bi bi-trash ms-1 me-2"></i>Delete
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default OrchidDetail;
