import { useEffect, useState } from "react";
import {
    getAllDocuments,
    createDocument,
    deleteDocument
} from "../services/documentService";
import LoadingSpinner from "../components/LoadingSpinner";
import ConfirmDialog from "../components/ConfirmDialog";

const emptyForm = { fileName: "", fileType: "", filePath: "" };

export default function Documents() {
    const [documents, setDocuments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [formData, setFormData] = useState(emptyForm);
    const [showForm, setShowForm] = useState(false);
    const [confirmDelete, setConfirmDelete] = useState(null);

    useEffect(() => {
        (async () => {
            try {
                const data = await getAllDocuments();
                setDocuments(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    const refresh = async () => {
        try {
            const data = await getAllDocuments();
            setDocuments(data);
        } catch (err) {
            setError(err.message);
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const openAdd = () => {
        setFormData(emptyForm);
        setShowForm(true);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await createDocument(formData);
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
            await deleteDocument(confirmDelete.id);
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
                <h2 className="mb-0">Documents</h2>
                <button className="btn btn-primary" onClick={openAdd}>Add Document</button>
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
                        <h5 className="card-title mb-3">Add Document</h5>
                        <form onSubmit={handleSubmit}>
                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" name="fileName" placeholder="File Name"
                                        value={formData.fileName} onChange={handleChange} required />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" name="fileType" placeholder="File Type (PDF, DOC, etc.)"
                                        value={formData.fileType} onChange={handleChange} required />
                                </div>
                                <div className="col-md-12 mb-3">
                                    <input className="form-control" name="filePath" placeholder="File Path"
                                        value={formData.filePath} onChange={handleChange} />
                                </div>
                            </div>
                            <button className="btn btn-success me-2" type="submit">Save</button>
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
                            <th>Type</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {documents.length === 0 ? (
                            <tr><td colSpan="4" className="text-center text-muted py-4">No documents found</td></tr>
                        ) : (
                            documents.map(doc => (
                                <tr key={doc.id}>
                                    <td>{doc.id}</td>
                                    <td>{doc.fileName}</td>
                                    <td>{doc.fileType || "-"}</td>
                                    <td>
                                        <button className="btn btn-sm btn-outline-danger" onClick={() => setConfirmDelete(doc)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            <ConfirmDialog
                open={!!confirmDelete}
                title="Delete Document"
                message={`Delete "${confirmDelete?.name}"?`}
                onConfirm={handleDelete}
                onCancel={() => setConfirmDelete(null)}
            />
        </div>
    );
}
