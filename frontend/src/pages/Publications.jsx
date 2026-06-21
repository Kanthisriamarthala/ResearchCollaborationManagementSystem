import { useEffect, useState } from "react";
import {
    getAllPublications,
    createPublication,
    updatePublication,
    deletePublication
} from "../services/publicationService";
import LoadingSpinner from "../components/LoadingSpinner";
import ConfirmDialog from "../components/ConfirmDialog";

const emptyForm = {
    title: "", authors: "", abstractText: "", keywords: "",
    publicationType: "JOURNAL", journalName: "", conferenceName: "",
    publicationDate: "", doi: "", status: "DRAFT"
};

export default function Publications() {
    const [publications, setPublications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [formData, setFormData] = useState(emptyForm);
    const [editing, setEditing] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [confirmDelete, setConfirmDelete] = useState(null);

    useEffect(() => {
        (async () => {
            try {
                const data = await getAllPublications();
                setPublications(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    const refresh = async () => {
        try {
            const data = await getAllPublications();
            setPublications(data);
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

    const openEdit = (pub) => {
        setEditing(pub);
        setFormData({
            title: pub.title || "",
            authors: pub.authors || "",
            abstractText: pub.abstractText || "",
            keywords: pub.keywords || "",
            publicationType: pub.publicationType || "JOURNAL",
            journalName: pub.journalName || "",
            conferenceName: pub.conferenceName || "",
            publicationDate: pub.publicationDate || "",
            doi: pub.doi || "",
            status: pub.status || "DRAFT"
        });
        setShowForm(true);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const payload = {
                ...formData,
                publicationDate: formData.publicationDate || null
            };
            if (editing) {
                await updatePublication(editing.id, payload);
            } else {
                await createPublication(payload);
            }
            setShowForm(false);
            setError("");
            await refresh();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleDelete = async () => {
        if (!confirmDelete) return;
        try {
            await deletePublication(confirmDelete.id);
            setConfirmDelete(null);
            await refresh();
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) return <LoadingSpinner />;

    return (
        <div className="page-content">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="mb-0">Publications</h2>
                <button className="btn btn-primary" onClick={openAdd}>Add Publication</button>
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
                        <h5 className="card-title mb-3">{editing ? "Edit Publication" : "Add Publication"}</h5>
                        <form onSubmit={handleSubmit}>
                            <div className="row">
                                <div className="col-md-12 mb-3">
                                    <input className="form-control" name="title" placeholder="Title"
                                        value={formData.title} onChange={handleChange} required />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" name="authors" placeholder="Authors"
                                        value={formData.authors} onChange={handleChange} required />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" name="journalName" placeholder="Journal"
                                        value={formData.journalName} onChange={handleChange} />
                                </div>
                                <div className="col-md-4 mb-3">
                                    <input className="form-control" name="publicationDate" placeholder="Publication Date" type="date"
                                        value={formData.publicationDate} onChange={handleChange} />
                                </div>
                                <div className="col-md-4 mb-3">
                                    <input className="form-control" name="doi" placeholder="DOI"
                                        value={formData.doi} onChange={handleChange} />
                                </div>
                                <div className="col-md-4 mb-3">
                                    <select className="form-select" name="publicationType"
                                        value={formData.publicationType} onChange={handleChange}>
                                        <option value="JOURNAL">Journal</option>
                                        <option value="CONFERENCE">Conference</option>
                                        <option value="BOOK_CHAPTER">Book Chapter</option>
                                        <option value="THESIS">Thesis</option>
                                        <option value="PATENT">Patent</option>
                                    </select>
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" name="keywords" placeholder="Keywords"
                                        value={formData.keywords} onChange={handleChange} />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <select className="form-select" name="status"
                                        value={formData.status} onChange={handleChange}>
                                        <option value="DRAFT">Draft</option>
                                        <option value="SUBMITTED">Submitted</option>
                                        <option value="ACCEPTED">Accepted</option>
                                        <option value="PUBLISHED">Published</option>
                                        <option value="REJECTED">Rejected</option>
                                    </select>
                                </div>
                                <div className="col-md-12 mb-3">
                                    <textarea className="form-control" name="abstractText" placeholder="Abstract" rows="3"
                                        value={formData.abstractText} onChange={handleChange} />
                                </div>
                            </div>
                            <button className="btn btn-success me-2" type="submit">{editing ? "Update" : "Save"}</button>
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
                            <th>Authors</th>
                            <th>Journal</th>
                            <th>Date</th>
                            <th>Type</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {publications.length === 0 ? (
                            <tr><td colSpan="7" className="text-center text-muted py-4">No publications found</td></tr>
                        ) : (
                            publications.map(pub => (
                                <tr key={pub.id}>
                                    <td>{pub.id}</td>
                                    <td>{pub.title}</td>
                                    <td>{pub.authors || "-"}</td>
                                    <td>{pub.journalName || "-"}</td>
                                    <td>{pub.publicationDate || "-"}</td>
                                    <td>{pub.publicationType || "-"}</td>
                                    <td>
                                        <button className="btn btn-sm btn-outline-primary me-2" onClick={() => openEdit(pub)}>Edit</button>
                                        <button className="btn btn-sm btn-outline-danger" onClick={() => setConfirmDelete(pub)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            <ConfirmDialog
                open={!!confirmDelete}
                title="Delete Publication"
                message={`Delete "${confirmDelete?.title}"?`}
                onConfirm={handleDelete}
                onCancel={() => setConfirmDelete(null)}
            />
        </div>
    );
}
