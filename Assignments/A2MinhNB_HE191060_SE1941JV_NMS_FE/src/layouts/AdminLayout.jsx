import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import Footer from "../components/Footer";
import { Outlet } from "react-router-dom";

const AdminLayout = () => {
    return (
        <>
            <Header />

            <div style={{ display: "flex" }}>
                <Sidebar />

                <main style={{ flex: 1, padding: "20px" }}>
                    <Outlet />
                </main>
            </div>

            <Footer />
        </>
    );
};

export default AdminLayout;
