import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; // Assuming you're using react-router

const Home = ({ setIsAuthenticated }) => {
  const [isTabVisible, setIsTabVisible] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const handleVisibilityChange = () => {
      if (document.hidden) {
        // Tab is hidden (user switched tabs)
        setIsTabVisible(false);
        // Optionally, show an alert or perform any action here
        window.alert(
          "You have switched to another tab. If you want to continue, you need to log out."
        );
      } else {
        // Tab is visible again (user switched back)
        if (!isTabVisible) {
          // If the tab was hidden, ask for logout
          const logoutConfirm = window.confirm("Do you want to log out?");
          if (logoutConfirm) {
            // Log out the user and redirect to login
            setIsAuthenticated(false);
            navigate("/login");
          }
        }
        setIsTabVisible(true);
      }
    };

    // Attach the event listener for visibility change
    document.addEventListener("visibilitychange", handleVisibilityChange);

    // Clean up the event listener when the component is unmounted
    return () => {
      document.removeEventListener("visibilitychange", handleVisibilityChange);
    };
  }, [isTabVisible, setIsAuthenticated, navigate]);

  return (
    <div>
      <h2>Welcome to the Home Page!</h2>
      {/* Your other content goes here */}
    </div>
  );
};

export default Home;
