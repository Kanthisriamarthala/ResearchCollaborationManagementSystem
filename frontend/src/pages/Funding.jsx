import { useEffect, useState } from "react";
import {
    getAllFunding,
    createFunding,
    updateFunding,
    deleteFunding
} from "../services/fundingService";
import LoadingSpinner from "../components/LoadingSpinner";
import ConfirmDialog from "../components/ConfirmDialog";

const emptyForm = { fundingAgency: "", amount: "", applicationDate: "", status: "APPLIED", remarks: "" };

export default function Funding() {
    const [records, setRecords] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [formData, setFormData] = useState(emptyForm);
    const [editing, setEditing] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [confirmDelete, setConfirmDelete] = useState(null);

    useEffect(() => {
        (async () => {
            try {
                const data = await getAllFunding();
                setRecords(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    const refresh = async () => {
        try {
            const data = await getAllFunding();
            setRecords(data);
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

    const openEdit = (rec) => {
        setEditing(rec);
        setFormData({
            fundingAgency: rec.fundingAgency || "",
            amount: rec.amount || "",
            applicationDate: rec.applicationDate || "",
            status: rec.status || "APPLIED",
            remarks: rec.remarks || ""
        });
        setShowForm(true);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const payload = {
                ...formData,
                amount: formData.amount ? Number(formData.amount) : null,
                applicationDate: formData.applicationDate || null
            };
            if (editing) {
                await updateFunding(editing.id, payload);
            } else {
                await createFunding(payload);
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
            await deleteFunding(confirmDelete.id);
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
                <h2 className="mb-0">Funding</h2>
                <button className="btn btn-primary" onClick={openAdd}>Add Funding</button>
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
                        <h5 className="card-title mb-3">{editing ? "Edit Funding" : "Add Funding"}</h5>
                        <form onSubmit={handleSubmit}>
                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <input className="form-control" name="fundingAgency" placeholder="Funding Agency"
                                        value={formData.fundingAgency} onChange={handleChange} required />
                                </div>
                                <div className="col-md-3 mb-3">
                                    <input className="form-control" name="amount" placeholder="Amount" type="number" step="0.01"
                                        value={formData.amount} onChange={handleChange} required />
                                </div>
                                <div className="col-md-3 mb-3">
                                    <input className="form-control" name="applicationDate" placeholder="Application Date" type="date"
                                        value={formData.applicationDate} onChange={handleChange} />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <select className="form-select" name="status"
                                        value={formData.status} onChange={handleChange}>
                                        <option value="APPLIED">Applied</option>
                                        <option value="UNDER_REVIEW">Under Review</option>
                                        <option value="APPROVED">Approved</option>
                                        <option value="REJECTED">Rejected</option>
                                        <option value="CLOSED">Closed</option>
                                    </select>
                                </div>
                                <div className="col-md-12 mb-3">
                                    <textarea className="form-control" name="remarks" placeholder="Remarks" rows="2"
                                        value={formData.remarks} onChange={handleChange} />
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
                            <th>Agency</th>
                            <th>Amount</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Remarks</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {records.length === 0 ? (
                            <tr><td colSpan="7" className="text-center text-muted py-4">No funding records found</td></tr>
                        ) : (
                            records.map(rec => (
                                <tr key={rec.id}>
                                    <td>{rec.id}</td>
                                    <td>{rec.fundingAgency || "-"}</td>
                                    <td>{rec.amount ?? "-"}</td>
                                    <td>{rec.applicationDate || "-"}</td>
                                    <td>{rec.status || "-"}</td>
                                    <td>{rec.remarks || "-"}</td>
                                    <td>
                                        <button className="btn btn-sm btn-outline-primary me-2" onClick={() => openEdit(rec)}>Edit</button>
                                        <button className="btn btn-sm btn-outline-danger" onClick={() => setConfirmDelete(rec)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            <ConfirmDialog
                open={!!confirmDelete}
                title="Delete Funding"
                message={`Delete funding from "${confirmDelete?.fundingAgency}"?`}
                onConfirm={handleDelete}
                onCancel={() => setConfirmDelete(null)}
            />
        </div>
    );
}
