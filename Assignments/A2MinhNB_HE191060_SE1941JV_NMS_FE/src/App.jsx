import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Category from "./pages/Category";
import News from "./pages/News";
import MyNews from "./pages/MyNews";
import Users from "./pages/Users";
import Tags from "./pages/Tags";
import Settings from "./pages/Settings";
import AdminLayout from "./layouts/AdminLayout";
import { createBrowserRouter, Navigate, RouterProvider } from "react-router-dom";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Navigate to="/login" />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
  {
    path: "/admin",
    element: <AdminLayout />,
    children: [
      {
        index: true,
        element: (() => {
          const account = JSON.parse(localStorage.getItem("funews_account") || "{}");
          return account.accountRole === 1 ? <Navigate to="dashboard" /> : <Navigate to="my-news" />;
        })(),
      },
      {
        path: "dashboard",
        element: <Dashboard />,
      },
      {
        path: "categories",
        element: <Category />,
      },
      {
        path: "tags",
        element: <Tags />,
      },
      {
        path: "news",
        element: <News />,
      },
      {
        path: "my-news",
        element: <MyNews />,
      },
      {
        path: "users",
        element: <Users />,
      },
      {
        path: "settings",
        element: <Settings />,
      },
    ],
  },
  {
    path: "*",
    element: <Navigate to="/login" />,
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
