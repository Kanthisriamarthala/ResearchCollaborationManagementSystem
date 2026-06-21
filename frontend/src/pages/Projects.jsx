import { useEffect, useState } from "react";
import {
    getAllProjects,
    createProject,
    updateProject,
    deleteProject,
    searchProjects
} from "../services/projectService";
import { getAllUsers } from "../services/userService";
import LoadingSpinner from "../components/LoadingSpinner";
import ConfirmDialog from "../components/ConfirmDialog";

const emptyForm = { title: "", description: "", researchDomain: "", status: "PLANNING", assignedUserId: "" };

export default function Projects() {
    const [projects, setProjects] = useState([]);
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [keyword, setKeyword] = useState("");
    const [formData, setFormData] = useState(emptyForm);
    const [editing, setEditing] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [confirmDelete, setConfirmDelete] = useState(null);

    useEffect(() => {
        (async () => {
            try {
                const [projData, userData] = await Promise.all([getAllProjects(), getAllUsers()]);
                setProjects(projData);
                setUsers(userData);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    const refreshProjects = async () => {
        try {
            const [projData, userData] = await Promise.all([getAllProjects(), getAllUsers()]);
            setProjects(projData);
            setUsers(userData);
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

    const openEdit = (project) => {
        setEditing(project);
        setFormData({
            title: project.title || "",
            description: project.description || "",
            researchDomain: project.researchDomain || "",
            status: project.status || "PLANNING",
            assignedUserId: String(project.assignedUserId ?? project.assignedUser?.id ?? "")
        });
        setShowForm(true);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const payload = {
                ...formData,
                assignedUserId: formData.assignedUserId ? Number(formData.assignedUserId) : null
            };
            if (editing) {
                await updateProject(editing.id, payload);
            } else {
                await createProject(payload);
            }
            setShowForm(false);
            setError("");
            await refreshProjects();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleDelete = async () => {
        if (!confirmDelete) return;
        try {
            await deleteProject(confirmDelete.id);
            setConfirmDelete(null);
            await refreshProjects();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleSearch = async () => {
        if (!keyword) { await refreshProjects(); return; }
        try {
            const data = await searchProjects(keyword);
            setProjects(data);
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) return <LoadingSpinner />;

    return (
        <div className="page-content">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="mb-0">Projects</h2>
                <button className="btn btn-primary" onClick={openAdd}>Add Project</button>
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
                        <h5 className="card-title mb-3">{editing ? "Edit Project" : "Add Project"}</h5>
                        <form onSubmit={handleSubmit}>
                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" placeholder="Title" name="title"
                                        value={formData.title} onChange={handleChange} required />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" placeholder="Research Domain" name="researchDomain"
                                        value={formData.researchDomain} onChange={handleChange} />
                                </div>
                                <div className="col-md-12 mb-3">
                                    <textarea className="form-control" placeholder="Description" name="description" rows="2"
                                        value={formData.description} onChange={handleChange} />
                                </div>
                                <div className="col-md-4 mb-3">
                                    <select className="form-select" name="status"
                                        value={formData.status} onChange={handleChange}>
                                        <option value="PLANNING">PLANNING</option>
                                        <option value="IN_PROGRESS">IN_PROGRESS</option>
                                        <option value="COMPLETED">COMPLETED</option>
                                    </select>
                                </div>
                                <div className="col-md-4 mb-3">
                                    <select className="form-select" name="assignedUserId"
                                        value={formData.assignedUserId} onChange={handleChange}>
                                        <option value="">Assign User</option>
                                        {users.map(u => (
                                            <option key={u.id} value={String(u.id)}>{u.firstName} {u.lastName}</option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <button className="btn btn-success me-2" type="submit">{editing ? "Update" : "Save"}</button>
                            <button className="btn btn-secondary" type="button" onClick={() => setShowForm(false)}>Cancel</button>
                        </form>
                    </div>
                </div>
            )}

            <div className="card mb-3">
                <div className="card-body">
                    <div className="row">
                        <div className="col-md-8">
                            <input className="form-control" placeholder="Search by keyword"
                                value={keyword} onChange={(e) => setKeyword(e.target.value)} />
                        </div>
                        <div className="col-md-4">
                            <button className="btn btn-success w-100" onClick={handleSearch}>Search</button>
                        </div>
                    </div>
                </div>
            </div>

            <div className="table-responsive">
                <table className="table table-bordered table-hover bg-white">
                    <thead className="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Domain</th>
                            <th>Status</th>
                            <th>Assigned User</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {projects.length === 0 ? (
                            <tr><td colSpan="6" className="text-center text-muted py-4">No projects found</td></tr>
                        ) : (
                            projects.map(project => (
                                <tr key={project.id}>
                                    <td>{project.id}</td>
                                    <td>{project.title}</td>
                                    <td>{project.researchDomain || "-"}</td>
                                    <td>{project.status}</td>
                                    <td>{project.assignedUser?.firstName || project.assignedUser?.name || "-"}</td>
                                    <td>
                                        <button className="btn btn-sm btn-outline-primary me-2" onClick={() => openEdit(project)}>Edit</button>
                                        <button className="btn btn-sm btn-outline-danger" onClick={() => setConfirmDelete(project)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            <ConfirmDialog
                open={!!confirmDelete}
                title="Delete Project"
                message={`Delete "${confirmDelete?.title}"?`}
                onConfirm={handleDelete}
                onCancel={() => setConfirmDelete(null)}
            />
        </div>
    );
}
