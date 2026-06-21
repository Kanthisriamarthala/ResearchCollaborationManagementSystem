import { useEffect, useState } from "react";
import {
    getAllTasks,
    createTask,
    updateTask,
    deleteTask
} from "../services/taskService";
import { getAllProjects } from "../services/projectService";
import { getAllUsers } from "../services/userService";
import LoadingSpinner from "../components/LoadingSpinner";
import ConfirmDialog from "../components/ConfirmDialog";

const emptyForm = {
    title: "", description: "", priority: "MEDIUM",
    status: "IN_PROGRESS", deadline: "", projectId: "", assignedUserId: ""
};

export default function Tasks() {
    const [tasks, setTasks] = useState([]);
    const [projects, setProjects] = useState([]);
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
                const [taskData, projData, userData] = await Promise.all([
                    getAllTasks(), getAllProjects(), getAllUsers()
                ]);
                setTasks(taskData);
                setProjects(projData);
                setUsers(userData);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    const refreshTasks = async () => {
        try {
            const [taskData, projData, userData] = await Promise.all([
                getAllTasks(), getAllProjects(), getAllUsers()
            ]);
            setTasks(taskData);
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

    const openEdit = (task) => {
        setEditing(task);
        setFormData({
            title: task.title || "",
            description: task.description || "",
            priority: task.priority || "MEDIUM",
            status: task.status || "IN_PROGRESS",
            deadline: task.deadline || "",
            projectId: String(task.project?.id ?? task.projectId ?? ""),
            assignedUserId: String(task.assignedUser?.id ?? task.assignedUserId ?? "")
        });
        setShowForm(true);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const payload = {
                title: formData.title,
                description: formData.description,
                priority: formData.priority,
                status: formData.status,
                deadline: formData.deadline || null,
                projectId: formData.projectId ? Number(formData.projectId) : null,
                assignedUserId: formData.assignedUserId ? Number(formData.assignedUserId) : null
            };
            if (editing) {
                await updateTask(editing.id, payload);
            } else {
                await createTask(payload);
            }
            setShowForm(false);
            setError("");
            await refreshTasks();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleDelete = async () => {
        if (!confirmDelete) return;
        try {
            await deleteTask(confirmDelete.id);
            setConfirmDelete(null);
            await refreshTasks();
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) return <LoadingSpinner />;

    return (
        <div className="page-content">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="mb-0">Tasks</h2>
                <button className="btn btn-primary" onClick={openAdd}>Add Task</button>
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
                        <h5 className="card-title mb-3">{editing ? "Edit Task" : "Add Task"}</h5>
                        <form onSubmit={handleSubmit}>
                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" name="title" placeholder="Title"
                                        value={formData.title} onChange={handleChange} required />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <select className="form-select" name="priority"
                                        value={formData.priority} onChange={handleChange}>
                                        <option value="LOW">LOW</option>
                                        <option value="MEDIUM">MEDIUM</option>
                                        <option value="HIGH">HIGH</option>
                                        <option value="CRITICAL">CRITICAL</option>
                                    </select>
                                </div>
                                <div className="col-md-12 mb-3">
                                    <textarea className="form-control" name="description" placeholder="Description" rows="2"
                                        value={formData.description} onChange={handleChange} />
                                </div>
                                <div className="col-md-4 mb-3">
                                    <select className="form-select" name="status"
                                        value={formData.status} onChange={handleChange}>
                                        <option value="TODO">TODO</option>
                                        <option value="IN_PROGRESS">IN_PROGRESS</option>
                                        <option value="DONE">DONE</option>
                                    </select>
                                </div>
                                <div className="col-md-4 mb-3">
                                    <input type="date" className="form-control" name="deadline"
                                        value={formData.deadline} onChange={handleChange} />
                                </div>
                                <div className="col-md-4 mb-3">
                                    <select className="form-select" name="projectId"
                                        value={formData.projectId} onChange={handleChange}>
                                        <option value="">Select Project</option>
                                        {projects.map(p => (
                                            <option key={p.id} value={String(p.id)}>{p.title}</option>
                                        ))}
                                    </select>
                                </div>
                                <div className="col-md-6 mb-3">
                                    <select className="form-select" name="assignedUserId"
                                        value={formData.assignedUserId} onChange={handleChange}>
                                        <option value="">Assign User</option>
                                        {users.map(u => (
                                            <option key={u.id} value={String(u.id)}>
                                                {u.firstName} {u.lastName}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <button className="btn btn-success me-2" type="submit">{editing ? "Update" : "Create"}</button>
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
                            <th>Title</th>
                            <th>Priority</th>
                            <th>Status</th>
                            <th>Deadline</th>
                            <th>Project</th>
                            <th>Assigned To</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tasks.length === 0 ? (
                            <tr><td colSpan="8" className="text-center text-muted py-4">No tasks found</td></tr>
                        ) : (
                            tasks.map(task => (
                                <tr key={task.id}>
                                    <td>{task.id}</td>
                                    <td>{task.title}</td>
                                    <td>{task.priority}</td>
                                    <td>{task.status}</td>
                                    <td>{task.deadline || "-"}</td>
                                    <td>{task.project?.title || "-"}</td>
                                    <td>{task.assignedUser?.firstName || task.assignedUser?.name || "-"}</td>
                                    <td>
                                        <button className="btn btn-sm btn-outline-primary me-2" onClick={() => openEdit(task)}>Edit</button>
                                        <button className="btn btn-sm btn-outline-danger" onClick={() => setConfirmDelete(task)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            <ConfirmDialog
                open={!!confirmDelete}
                title="Delete Task"
                message={`Delete "${confirmDelete?.title}"?`}
                onConfirm={handleDelete}
                onCancel={() => setConfirmDelete(null)}
            />
        </div>
    );
}
