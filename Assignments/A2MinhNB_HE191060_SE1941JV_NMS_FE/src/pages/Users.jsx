import { useEffect, useState } from "react";
import { Button, Table, Form } from "react-bootstrap";
import {
    getAccounts,
    createAccount,
    updateAccount,
    deleteAccount,
} from "../services/accountService";
import AccountModal from "../components/AccountModal";
import ConfirmDialog from "../components/ConfirmDialog";

const Users = () => {
    const [accounts, setAccounts] = useState([]);
    const [search, setSearch] = useState("");
    const [selected, setSelected] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [showConfirm, setShowConfirm] = useState(false);

    const fetchAccounts = async () => {
        try {
            const res = await getAccounts();
            setAccounts(res.data || []);
            setError("");
        } catch (err) {
            setError("Không thể tải danh sách người dùng");
        }
    };

    useEffect(() => {
        fetchAccounts();
    }, []);

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    // FE search
    const filteredAccounts = accounts.filter(
        (a) =>
            a.accountName.toLowerCase().includes(search.toLowerCase()) ||
            a.accountEmail.toLowerCase().includes(search.toLowerCase())
    );

    const handleSave = async (data) => {
        setLoading(true);
        setError("");
        setSuccess("");
        try {
            if (!data.accountName || !data.accountEmail) throw new Error("Required fields missing");
            if (selected) {
                await updateAccount(selected.id, data);
                setSuccess("Cập nhật người dùng thành công");
            } else {
                await createAccount(data);
                setSuccess("Tạo người dùng thành công");
            }
            setShowModal(false);
            await fetchAccounts();
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
            await deleteAccount(selected.id);
            setShowConfirm(false);
            setSuccess("Xoá người dùng thành công");
            await fetchAccounts();
        } catch (err) {
            setError(err?.response?.data?.message || "Không thể xóa người dùng");
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <div className="d-flex justify-content-between mb-3">
                <Form.Control
                    placeholder="Search user..."
                    style={{ width: 300 }}
                    onChange={(e) => setSearch(e.target.value)}
                />
                <Button
                    onClick={() => {
                        setSelected(null);
                        setShowModal(true);
                    }}
                >
                    Create User
                </Button>
            </div>

            <Table bordered hover>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th width="160">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredAccounts.map((a) => (
                        <tr key={a.id}>
                            <td>{a.accountName}</td>
                            <td>{a.accountEmail}</td>
                            <td>{a.accountRole === 1 ? "Admin" : "Staff"}</td>
                            <td>
                                <Button
                                    size="sm"
                                    variant="outline-primary"
                                    className="me-2"
                                    onClick={() => {
                                        setSelected(a);
                                        setShowModal(true);
                                    }}
                                >
                                    Edit
                                </Button>
                                <Button
                                    size="sm"
                                    variant="outline-danger"
                                    onClick={() => {
                                        setSelected(a);
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

            <AccountModal
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
                title="Delete User"
                message="Are you sure you want to delete this user?"
            />

            {error && <div className="mt-2"><div className="alert alert-danger">{error}</div></div>}
            {success && <div className="mt-2"><div className="alert alert-success">{success}</div></div>}
        </>
    );
};

export default Users;
