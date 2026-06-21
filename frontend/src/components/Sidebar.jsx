import { Link, useLocation } from "react-router-dom";

const links = [
    { to: "/", label: "Dashboard", icon: "📊" },
    { to: "/users", label: "Users", icon: "👥" },
    { to: "/projects", label: "Projects", icon: "📁" },
    { to: "/tasks", label: "Tasks", icon: "✅" },
    { to: "/publications", label: "Publications", icon: "📄" },
    { to: "/funding", label: "Funding", icon: "💰" },
    { to: "/documents", label: "Documents", icon: "📎" },
];

function Sidebar() {
    const location = useLocation();

    return (
        <div className="sidebar">

            <div className="sidebar-header">
                <h5>RCMS</h5>
                <small>Research Collaboration Management System</small>
            </div>

            <div className="sidebar-nav">
                {links.map(link => (
                    <Link
                        key={link.to}
                        className={`sidebar-link${location.pathname === link.to ? " active" : ""}`}
                        to={link.to}
                    >
                        <span className="icon">{link.icon}</span>
                        {link.label}
                    </Link>
                ))}
            </div>

        </div>
    );
}

export default Sidebar;