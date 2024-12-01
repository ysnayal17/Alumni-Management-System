import "./App.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Login from "./pages/Login.jsx";
import { useState } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import Home from "./pages/Home.jsx";
import AlumnisLIst from "./pages/AlumnisLIst.jsx";
import Event from "./pages/Event";
import JobsList from "./pages/JobsList.jsx";
function App() {
  //states
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  console.log(isAuthenticated);
  return (
    <>
      {/* <button onClick={notify}>Notify!</button> */}

      <Router>
        <Routes>
          {/* Define your main routes */}
          <Route
            path="/login"
            element={
              <Login
                setIsAuthenticated={setIsAuthenticated}
                isAuthenticated={isAuthenticated}
              />
            }
          />

          {/* Home Route: Show Home if authenticated and authorized */}
          <Route
            path="/home"
            element={
              true ? (
                <Home setIsAuthenticated={setIsAuthenticated} />
              ) : (
                <Navigate to="/login" />
              )
            }
          />
          <Route
            path="/alumnis"
            element={
              true ? (
                <AlumnisLIst setIsAuthenticated={setIsAuthenticated} />
              ) : (
                <Navigate to="/login" />
              )
            }
          />
          <Route
            path="/jobs"
            element={
              true ? (
                <JobsList setIsAuthenticated={setIsAuthenticated} />
              ) : (
                <Navigate to="/login" />
              )
            }
          />
          <Route
            path="/events"
            element={
              true ? (
                <Event setIsAuthenticated={setIsAuthenticated} />
              ) : (
                <Navigate to="/login" />
              )
            }
          />

          {/* Unauthorized Route: Show Forbidden if authenticated but not authorized */}
          {/* <Route
            path="/forbidden"
            element={
              isAuthenticated && !isAuthorized ? (
                <Forbidden />
              ) : (
                <Navigate to="/login" />
              )
            }
          /> */}
          {/* Catch-all route: Redirect any undefined paths to /login */}
          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </Router>
      <ToastContainer />
    </>
  );
}

export default App;
