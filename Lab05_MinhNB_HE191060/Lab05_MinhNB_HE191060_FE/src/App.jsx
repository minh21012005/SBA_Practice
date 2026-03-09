import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavBar from './components/NavBar';
import OrchidListPage from './pages/OrchidListPage';
import CreateOrchidPage from './pages/CreateOrchidPage';
import EditOrchidPage from './pages/EditOrchidPage';
import OrchidDetail from './components/OrchidDetail';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

function App() {
  return (
    <Router>
      <NavBar />
      <div className="container">
        <Routes>
          <Route path="/" element={<OrchidListPage />} />
          <Route path="/orchids" element={<OrchidListPage />} />
          <Route path="/orchids/new" element={<CreateOrchidPage />} />
          <Route path="/orchids/edit/:id" element={<EditOrchidPage />} />
          <Route path="/orchids/:id" element={<OrchidDetail />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
