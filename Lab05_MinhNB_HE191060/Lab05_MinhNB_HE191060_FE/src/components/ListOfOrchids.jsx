import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getAllOrchids, deleteOrchid } from '../services/orchidService';

const ListOfOrchids = () => {
    const [orchids, setOrchids] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchOrchids = async () => {
        try {
            setLoading(true);
            const response = await getAllOrchids();
            setOrchids(response.data);
            setError(null);
        } catch (err) {
            console.error("Error fetching orchids:", err);
            setError("Failed to load orchids. Please make sure the backend is running.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchOrchids();
    }, []);

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this orchid?")) {
            try {
                await deleteOrchid(id);
                setOrchids(orchids.filter(orchid => orchid.orchidId !== id));
            } catch (err) {
                console.error("Error deleting orchid:", err);
                alert("Failed to delete the orchid. Please try again.");
            }
        }
    };

    if (loading) return <div className="text-center mt-5"><div className="spinner-border text-primary" role="status"></div></div>;
    if (error) return <div className="alert alert-danger mt-3" role="alert">{error}</div>;

    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Orchids List</h2>
                <Link to="/orchids/new" className="btn btn-primary">Add New Orchid</Link>
            </div>

            <div className="table-responsive shadow-sm rounded">
                <table className="table table-striped table-hover mb-0">
                    <thead className="table-dark">
                        <tr>
                            <th>Image</th>
                            <th>Name</th>
                            <th>Original</th>
                            <th className="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orchids.length === 0 ? (
                            <tr>
                                <td colSpan="4" className="text-center py-4">No orchids found.</td>
                            </tr>
                        ) : (
                            orchids.map(orchid => (
                                <tr key={orchid.orchidId} className="align-middle">
                                    <td>
                                        {orchid.orchidUrl ? (
                                            <img
                                                src={orchid.orchidUrl}
                                                alt={orchid.orchidName}
                                                style={{ width: '60px', height: '60px', objectFit: 'cover', borderRadius: '5px' }}
                                                onError={(e) => { e.target.onerror = null; e.target.src = "https://via.placeholder.com/60?text=No+Img"; }}
                                            />
                                        ) : (
                                            <div className="bg-light d-flex align-items-center justify-content-center text-muted" style={{ width: '60px', height: '60px', borderRadius: '5px' }}>
                                                <small>No Img</small>
                                            </div>
                                        )}
                                    </td>
                                    <td className="fw-semibold">{orchid.orchidName}</td>
                                    <td><span className="badge bg-secondary">{orchid.orchidCategory}</span></td>
                                    <td className="text-center">
                                        <Link to={`/orchids/${orchid.orchidId}`} className="btn btn-sm btn-info me-2 text-white">View</Link>
                                        <Link to={`/orchids/edit/${orchid.orchidId}`} className="btn btn-sm btn-warning me-2">Edit</Link>
                                        <button onClick={() => handleDelete(orchid.orchidId)} className="btn btn-sm btn-danger">Delete</button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListOfOrchids;
