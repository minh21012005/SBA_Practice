import { Link, NavLink } from 'react-router-dom';

const NavBar = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark mb-4 shadow-sm">
            <div className="container">
                <Link className="navbar-brand fw-bold" to="/">Orchid Management</Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-toggle="target" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav me-auto">
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/orchids" end>List of Orchids</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/orchids/new">Add Orchid</NavLink>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default NavBar;
