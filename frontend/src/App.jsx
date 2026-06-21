import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";
import Sidebar from "./components/Sidebar";

import Dashboard from "./pages/Dashboard";
import Users from "./pages/Users";
import Projects from "./pages/Projects";
import Tasks from "./pages/Tasks";
import Publications from "./pages/Publications";
import Funding from "./pages/Funding";
import Documents from "./pages/Documents";

function App() {
  return (
    <BrowserRouter>

      <Navbar />

      <div className="d-flex">

        <Sidebar />

        <div className="flex-grow-1" style={{ minWidth: 0 }}>

          <Routes>

            <Route path="/" element={<Dashboard />} />
            <Route path="/users" element={<Users />} />
            <Route path="/projects" element={<Projects />} />
            <Route path="/tasks" element={<Tasks />} />
            <Route path="/publications" element={<Publications />} />
            <Route path="/funding" element={<Funding />} />
            <Route path="/documents" element={<Documents />} />

          </Routes>

        </div>

      </div>

    </BrowserRouter>
  );
}

export default App;