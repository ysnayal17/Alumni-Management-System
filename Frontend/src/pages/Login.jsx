import React, { useState } from "react";
import { toast } from "react-toastify";
const Login = ({setIsAuthenticated, isAuthenticated}) => {

  //states
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  //methods
  const handleSubmit = async (e) => {
    e.preventDefault();
    // Handle login logic here, e.g., API call or validation
// Frontend validation: Check if email and password are provided
    if (!email || !password) {
      if (!email) {
        toast.error("Please enter your email!");
      }
      if (!password) {
        toast.error("Please enter your password!");
      }
      return; // Don't proceed if validation fails
    }

    // Make the API call for login
    try {
      // const response = await axios.post("http://localhost:5000/api/login", {
      //   email,
      //   password,
      // });
      setIsAuthenticated(true);
      window.location.pathname = "/home";
      // Check for successful login
    //   if (response.status === 200) {
    //     toast.success("Login successful!");
    //   setIsAuthenticated(true);
    //     console.log(response.data); // Process login success
    //   }
    } catch (error) {
    //   if (error.response) {
    //     // Handle server-side errors
    //     toast.error(error.response.data.error || "Login failed!");
    //   } else {
    //     // Handle client-side or network errors
    //     toast.error("An error occurred, please try again later.");
    //   }
    }
  };

  //JSX
  return (
    <>
      <section className="h-screen">
        <div className="container h-full px-6 py-12">
          <div className="g-6 flex h-full flex-wrap items-center justify-center lg:justify-between">
            {/* <!-- Left column container with background--> */}
            <div className="mb-12 md:mb-0 md:w-8/12 lg:w-6/12">
              <img
                src="https://tecdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg"
                className="w-full"
                alt="Phone image"
              />
            </div>

            {/* <!-- Right column container with form --> */}
            <div className="md:w-8/12 lg:ml-6 lg:w-5/12">
              <form className="lg:w-3/4 " onSubmit={handleSubmit}>
                {/* <!-- Email input --> */}
                <label className="block font-serif text-gray-700 text-sm  mb-2">
                  Email Address
                </label>
                <input
                  className="text-gray-700 tracking-wider font-serif border border-gray-300 rounded py-2 px-4 block w-full focus:outline-2 focus:outline-blue-700"
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />

                {/* <!--Password input--> */}
                <label className="block text-gray-700 text-sm font-serif mb-2 my-3">
                  Password
                </label>
                <input
                  // className="text-gray-700 border border-gray-300 rounded py-2 px-4 block w-full focus:outline-2 focus:outline-blue-700"
                  type="password"
                  className="w-full px-4 py-2 tracking-widest border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />

                {/* <!-- Remember me checkbox --> */}

                <button
                  type="submit"
                  className="inline-block bg-custom-gradient my-6 w-full rounded bg-primary px-7 pb-2.5 pt-3 text-sm font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
                >
                  Sign in
                </button>
              </form>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default Login;
