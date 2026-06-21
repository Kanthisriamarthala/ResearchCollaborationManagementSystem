import { useEffect, useState } from "react";
import {
    getAllUsers,
    createUser,
    updateUser,
    deleteUser
} from "../services/userService";
import LoadingSpinner from "../components/LoadingSpinner";
import ConfirmDialog from "../components/ConfirmDialog";

const emptyForm = {
    firstName: "", lastName: "", email: "",
    phoneNumber: "", department: "", designation: "", researchInterests: ""
};

export default function Users() {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [formData, setFormData] = useState(emptyForm);
    const [editing, setEditing] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [confirmDelete, setConfirmDelete] = useState(null);

    useEffect(() => {
        (async () => {
            try {
                const data = await getAllUsers();
                setUsers(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    const refreshUsers = async () => {
        try {
            const data = await getAllUsers();
            setUsers(data);
        } catch (err) {
            setError(err.message);
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const openAdd = () => {
        setEditing(null);
        setFormData(emptyForm);
        setShowForm(true);
    };

    const openEdit = (user) => {
        setEditing(user);
        setFormData({
            firstName: user.firstName || "",
            lastName: user.lastName || "",
            email: user.email || "",
            phoneNumber: user.phoneNumber || "",
            department: user.department || "",
            designation: user.designation || "",
            researchInterests: user.researchInterests || ""
        });
        setShowForm(true);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editing) {
                await updateUser(editing.id, formData);
            } else {
                await createUser(formData);
            }
            setShowForm(false);
            setError("");
            await refreshUsers();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleDelete = async () => {
        if (!confirmDelete) return;
        try {
            await deleteUser(confirmDelete.id);
            setConfirmDelete(null);
            await refreshUsers();
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) return <LoadingSpinner />;

    return (
        <div className="page-content">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="mb-0">Users</h2>
                <button className="btn btn-primary" onClick={openAdd}>Add User</button>
            </div>

            {error && (
                <div className="alert alert-danger alert-dismissible fade show">
                    {error}
                    <button type="button" className="btn-close" onClick={() => setError("")}></button>
                </div>
            )}

            {showForm && (
                <div className="card mb-4">
                    <div className="card-body">
                        <h5 className="card-title mb-3">{editing ? "Edit User" : "Add User"}</h5>
                        <form onSubmit={handleSubmit}>
                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" placeholder="First Name" name="firstName"
                                        value={formData.firstName} onChange={handleChange} required />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" placeholder="Last Name" name="lastName"
                                        value={formData.lastName} onChange={handleChange} required />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" placeholder="Email" name="email" type="email"
                                        value={formData.email} onChange={handleChange} required />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" placeholder="Phone" name="phoneNumber"
                                        value={formData.phoneNumber} onChange={handleChange} />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" placeholder="Department" name="department"
                                        value={formData.department} onChange={handleChange} />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" placeholder="Designation" name="designation"
                                        value={formData.designation} onChange={handleChange} />
                                </div>
                                <div className="col-md-12 mb-3">
                                    <textarea className="form-control" placeholder="Research Interests" name="researchInterests"
                                        value={formData.researchInterests} onChange={handleChange} rows="2" />
                                </div>
                            </div>
                            <button className="btn btn-success me-2" type="submit">
                                {editing ? "Update" : "Save"}
                            </button>
                            <button className="btn btn-secondary" type="button" onClick={() => setShowForm(false)}>Cancel</button>
                        </form>
                    </div>
                </div>
            )}

            <div className="table-responsive">
                <table className="table table-bordered table-hover bg-white">
                    <thead className="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Department</th>
                            <th>Designation</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.length === 0 ? (
                            <tr><td colSpan="6" className="text-center text-muted py-4">No users found</td></tr>
                        ) : (
                            users.map(user => (
                                <tr key={user.id}>
                                    <td>{user.id}</td>
                                    <td>{user.firstName} {user.lastName}</td>
                                    <td>{user.email}</td>
                                    <td>{user.department || "-"}</td>
                                    <td>{user.designation || "-"}</td>
                                    <td>
                                        <button className="btn btn-sm btn-outline-primary me-2" onClick={() => openEdit(user)}>Edit</button>
                                        <button className="btn btn-sm btn-outline-danger" onClick={() => setConfirmDelete(user)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            <ConfirmDialog
                open={!!confirmDelete}
                title="Delete User"
                message={`Are you sure you want to delete "${confirmDelete?.firstName} ${confirmDelete?.lastName}"?`}
                onConfirm={handleDelete}
                onCancel={() => setConfirmDelete(null)}
            />
        </div>
    );
}
