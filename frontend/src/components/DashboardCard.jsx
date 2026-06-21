const colorMap = {
    indigo: { bg: "rgba(99,102,241,0.1)", text: "#6366f1", deco: "#6366f1", trend: "#10b981" },
    sky: { bg: "rgba(14,165,233,0.1)", text: "#0ea5e9", deco: "#0ea5e9", trend: "#10b981" },
    emerald: { bg: "rgba(16,185,129,0.1)", text: "#10b981", deco: "#10b981", trend: "#10b981" },
    amber: { bg: "rgba(245,158,11,0.1)", text: "#f59e0b", deco: "#f59e0b", trend: "#ef4444" },
};

function DashboardCard({ title, value, icon, color = "indigo", trend }) {
    const c = colorMap[color] || colorMap.indigo;

    return (
        <div className="stat-card">
            <div className="icon-wrapper" style={{ background: c.bg, color: c.text }}>
                {icon || "📊"}
            </div>
            <div className="stat-value" style={{ color: c.text }}>
                {value}
            </div>
            <p className="stat-label">{title}</p>
            {trend !== undefined && (
                <div className="stat-trend" style={{ color: trend >= 0 ? "#10b981" : "#ef4444" }}>
                    {trend >= 0 ? "↑" : "↓"} {Math.abs(trend)}% from last month
                </div>
            )}
            <div className="stat-decoration" style={{ background: c.deco }} />
        </div>
    );
}

export default DashboardCard;