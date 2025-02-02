import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const VendorRegister = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    username: "",
    password: "",
  });

  const [responseMessage, setResponseMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false); // For animation

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleVendorRegister = async (e) => {
    e.preventDefault();
    setResponseMessage("");
    setErrorMessage("");
    setIsLoading(false); // Reset animation

    try {
      const response = await fetch(
        "http://localhost:8080/api/vendor/register",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(formData), // Convert formData to JSON string
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error("Failed to register");
      }

      // Parse JSON response to confirm success
      const data = await response.json();
      if (data.success) {
        setResponseMessage(
          `Successfully registered vendor, ${formData.username}!`
        );
        setIsLoading(true); // Start the animation

        // Delay the navigation
        setTimeout(() => {
          navigate("/vendor-login");
        }, 2000); // 2-second delay
      } else {
        throw new Error(data.message || "Vendor already exists!");
      }
    } catch (error) {
      setErrorMessage(error.message || "Something went wrong...");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-blue-50">
      <button
        className="absolute top-8 left-10 py-2 px-4 bg-blue-500 text-white font-semibold rounded shadow-lg hover:bg-blue-600 transition-all hover:scale-105"
        onClick={() => navigate("/")}>
        Back
      </button>
      <div className="w-full max-w-md bg-white p-6 rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">
          Vendor Register
        </h2>
        <form onSubmit={handleVendorRegister} className="space-y-4">
          <div>
            <label
              htmlFor="name"
              className="block text-sm font-medium text-gray-700">
              Name
            </label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-[#4CAF50] focus:border-[#4CAF50]"
              required
            />
          </div>

          <div>
            <label
              htmlFor="email"
              className="block text-sm font-medium text-gray-700">
              Email
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-[#4CAF50] focus:border-[#4CAF50]"
              required
            />
          </div>

          <div>
            <label
              htmlFor="username"
              className="block text-sm font-medium text-gray-700">
              Username
            </label>
            <input
              type="text"
              id="username"
              name="username"
              value={formData.username}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-[#4CAF50] focus:border-[#4CAF50]"
              required
            />
          </div>

          <div>
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-700">
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-[#4CAF50] focus:border-[#4CAF50]"
              required
            />
          </div>

          <div>
            <button
              type="submit"
              className="w-full py-2 px-4 bg-[#4CAF50] text-white font-semibold rounded-md shadow hover:bg-[#45a049] transition-all">
              Register
            </button>
          </div>
        </form>

        {responseMessage && (
          <div className="mt-4 p-4 bg-green-100 text-green-800 rounded">
            {responseMessage}
          </div>
        )}

        {errorMessage && (
          <div className="mt-4 p-4 bg-red-100 text-red-800 rounded">
            {errorMessage}
          </div>
        )}

        {isLoading && (
          <div className="fixed inset-0 flex items-center justify-center bg-gray-50 bg-opacity-75 z-50">
            <div className="flex flex-col items-center">
              <div className="animate-spin rounded-full h-16 w-16 border-t-4 border-blue-500"></div>
              <p className="mt-4 text-gray-700 text-lg">Redirecting...</p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default VendorRegister;
