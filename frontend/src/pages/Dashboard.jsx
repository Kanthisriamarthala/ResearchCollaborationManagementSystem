import { useEffect, useState } from "react";
import { getDashboardSummary } from "../services/dashboardService";
import DashboardCard from "../components/DashboardCard";
import LoadingSpinner from "../components/LoadingSpinner";

export default function Dashboard() {
    const [dashboard, setDashboard] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        (async () => {
            try {
                const data = await getDashboardSummary();
                setDashboard(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    if (loading) return <LoadingSpinner />;

    return (
        <div className="page-content">
            <div className="page-header">
                <h2>Dashboard</h2>
                <p>Overview of research collaboration system</p>
            </div>

            {error && (
                <div className="alert alert-danger alert-dismissible fade show">
                    {error}
                    <button type="button" className="btn-close" onClick={() => setError("")}></button>
                </div>
            )}

            <div className="row g-3 mb-4">
                <div className="col-md-3">
                    <DashboardCard title="Total Users" value={dashboard?.totalUsers || 0} icon="👥" color="indigo" trend={12} />
                </div>
                <div className="col-md-3">
                    <DashboardCard title="Total Projects" value={dashboard?.totalProjects || 0} icon="📁" color="sky" trend={8} />
                </div>
                <div className="col-md-3">
                    <DashboardCard title="Total Tasks" value={dashboard?.totalTasks || 0} icon="✅" color="emerald" trend={-3} />
                </div>
                <div className="col-md-3">
                    <DashboardCard title="Publications" value={dashboard?.totalPublications || 0} icon="📄" color="amber" trend={15} />
                </div>
            </div>

            <div className="row g-3">
                <div className="col-md-6">
                    <div className="dashboard-activity">
                        <h5>🔔 Recent Activity</h5>
                        <div className="activity-item">
                            <span className="activity-dot" style={{ background: "#6366f1" }} />
                            <span className="activity-text">System is running smoothly</span>
                            <span className="activity-time">Just now</span>
                        </div>
                        <div className="activity-item">
                            <span className="activity-dot" style={{ background: "#10b981" }} />
                            <span className="activity-text">{dashboard?.totalUsers || 0} users registered</span>
                            <span className="activity-time">Today</span>
                        </div>
                        <div className="activity-item">
                            <span className="activity-dot" style={{ background: "#0ea5e9" }} />
                            <span className="activity-text">{dashboard?.totalProjects || 0} projects created</span>
                            <span className="activity-time">This week</span>
                        </div>
                        <div className="activity-item">
                            <span className="activity-dot" style={{ background: "#f59e0b" }} />
                            <span className="activity-text">{dashboard?.totalPublications || 0} publications submitted</span>
                            <span className="activity-time">This month</span>
                        </div>
                    </div>
                </div>
                <div className="col-md-6">
                    <div className="dashboard-activity">
                        <h5>📈 Quick Stats</h5>
                        <div className="activity-item">
                            <span className="activity-dot" style={{ background: "#6366f1" }} />
                            <span className="activity-text">Active projects</span>
                            <span className="activity-time fw-bold">{dashboard?.totalProjects || 0}</span>
                        </div>
                        <div className="activity-item">
                            <span className="activity-dot" style={{ background: "#10b981" }} />
                            <span className="activity-text">Total tasks</span>
                            <span className="activity-time fw-bold">{dashboard?.totalTasks || 0}</span>
                        </div>
                        <div className="activity-item">
                            <span className="activity-dot" style={{ background: "#0ea5e9" }} />
                            <span className="activity-text">Publications</span>
                            <span className="activity-time fw-bold">{dashboard?.totalPublications || 0}</span>
                        </div>
                        <div className="activity-item">
                            <span className="activity-dot" style={{ background: "#f59e0b" }} />
                            <span className="activity-text">Funding records</span>
                            <span className="activity-time fw-bold">{dashboard?.totalFunding || 0}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
