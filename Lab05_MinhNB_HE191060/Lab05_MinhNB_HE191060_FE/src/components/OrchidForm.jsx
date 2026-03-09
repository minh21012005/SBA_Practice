import { useState, useEffect } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';
import { createOrchid, getOrchidById, updateOrchid } from '../services/orchidService';

const OrchidForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const isEditMode = Boolean(id);

    const [formData, setFormData] = useState({
        orchidName: '',
        natural: false,
        orchidDescription: '',
        orchidCategory: '',
        attractive: false,
        orchidUrl: ''
    });

    const [loading, setLoading] = useState(isEditMode);
    const [error, setError] = useState(null);
    const [saving, setSaving] = useState(false);

    useEffect(() => {
        if (isEditMode) {
            const fetchOrchid = async () => {
                try {
                    const response = await getOrchidById(id);
                    setFormData(response.data);
                    setError(null);
                } catch (err) {
                    console.error("Error fetching orchid:", err);
                    setError("Failed to load orchid details. It might have been deleted.");
                } finally {
                    setLoading(false);
                }
            };
            fetchOrchid();
        }
    }, [id, isEditMode]);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setSaving(true);

        try {
            if (isEditMode) {
                await updateOrchid(id, formData);
                alert("Orchid updated successfully!");
            } else {
                await createOrchid(formData);
                alert("Orchid created successfully!");
            }
            navigate('/orchids');
        } catch (err) {
            console.error("Error saving orchid:", err);
            setError("Failed to save the orchid. Please try again.");
            setSaving(false);
        }
    };

    if (loading) return <div className="text-center mt-5"><div className="spinner-border text-primary" role="status"></div></div>;

    return (
        <div className="container mt-4">
            <div className="card shadow-sm mx-auto" style={{ maxWidth: '800px' }}>
                <div className="card-header bg-primary text-white">
                    <h3 className="mb-0">{isEditMode ? 'Edit Orchid' : 'Add New Orchid'}</h3>
                </div>
                <div className="card-body p-4">
                    {error && <div className="alert alert-danger">{error}</div>}

                    <form onSubmit={handleSubmit}>
                        <div className="row mb-3">
                            <div className="col-md-6">
                                <label htmlFor="orchidName" className="form-label fw-semibold">Orchid Name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="orchidName"
                                    name="orchidName"
                                    value={formData.orchidName}
                                    onChange={handleChange}
                                    required
                                    placeholder="e.g. Phalaenopsis"
                                />
                            </div>
                            <div className="col-md-6">
                                <label htmlFor="orchidCategory" className="form-label fw-semibold">Category</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="orchidCategory"
                                    name="orchidCategory"
                                    value={formData.orchidCategory}
                                    onChange={handleChange}
                                    required
                                    placeholder="e.g. Epiphyte"
                                />
                            </div>
                        </div>

                        <div className="mb-3">
                            <label htmlFor="orchidUrl" className="form-label fw-semibold">Image URL</label>
                            <input
                                type="url"
                                className="form-control"
                                id="orchidUrl"
                                name="orchidUrl"
                                value={formData.orchidUrl}
                                onChange={handleChange}
                                placeholder="https://example.com/image.jpg"
                            />
                        </div>

                        <div className="mb-3">
                            <label htmlFor="orchidDescription" className="form-label fw-semibold">Description</label>
                            <textarea
                                className="form-control"
                                id="orchidDescription"
                                name="orchidDescription"
                                rows="4"
                                value={formData.orchidDescription}
                                onChange={handleChange}
                                placeholder="Detailed description of the orchid..."
                            ></textarea>
                        </div>

                        <div className="row mb-4">
                            <div className="col-md-6">
                                <div className="form-check form-switch mt-2">
                                    <input
                                        className="form-check-input"
                                        type="checkbox"
                                        id="natural"
                                        name="natural"
                                        checked={formData.natural}
                                        onChange={handleChange}
                                    />
                                    <label className="form-check-label" htmlFor="natural">Is Natural (Wild)</label>
                                </div>
                            </div>
                            <div className="col-md-6">
                                <div className="form-check form-switch mt-2">
                                    <input
                                        className="form-check-input"
                                        type="checkbox"
                                        id="attractive"
                                        name="attractive"
                                        checked={formData.attractive}
                                        onChange={handleChange}
                                    />
                                    <label className="form-check-label" htmlFor="attractive">Is Attractive (Show Quality)</label>
                                </div>
                            </div>
                        </div>

                        <div className="d-flex justify-content-end gap-2 mt-4 pt-3 border-top">
                            <Link to="/orchids" className="btn btn-secondary">Cancel</Link>
                            <button type="submit" className="btn btn-primary" disabled={saving}>
                                {saving ? (
                                    <><span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>Saving...</>
                                ) : (
                                    isEditMode ? 'Update' : 'Create'
                                )}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default OrchidForm;
