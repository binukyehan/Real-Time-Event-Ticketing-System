import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const CustomerDelete = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    confirmation: "",
  });

  const [responseMessage, setResponseMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false); // For animation

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleCustomerDelete = async (e) => {
    e.preventDefault();
    setResponseMessage("");
    setErrorMessage("");
    setIsLoading(false); // Reset animation

    try {
      const response = await fetch(
        "http://localhost:8080/api/customer/delete",
        {
          method: "DELETE",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(formData), // Convert formData to JSON string
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error("Failed to log in");
      }

      // Parse JSON response to confirm success
      const data = await response.json();
      if (data.success) {
        setResponseMessage(
          `Account ${formData.username} Deleted successfully!`
        );
        setIsLoading(true); // Start the animation

        // Delay the navigation
        setTimeout(() => {
          navigate("/customer-login");
        }, 2000); // 2-second delay
      } else {
        throw new Error(data.message || "Invalid credentials");
      }
    } catch (error) {
      setErrorMessage(error.message || "Something went wrong...");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-blue-50">
      <button
        className="absolute top-8 left-10 py-2 px-4 bg-blue-500 text-white font-semibold rounded shadow-lg hover:bg-blue-600 transition-all hover:scale-105"
        onClick={() => navigate("/purchase-tickets")}>
        Back
      </button>
      <div className="w-full max-w-md bg-white p-6 rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">
          Customer Account Delete
        </h2>
        <form onSubmit={handleCustomerDelete} className="space-y-4">
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
            <label
              htmlFor="confirmation"
              className="block text-sm font-medium text-gray-700">
              Confirmation
            </label>
            <input
              type="text"
              id="confirmation"
              name="confirmation"
              value={formData.confirmation}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-[#4CAF50] focus:border-[#4CAF50]"
              required
            />
          </div>

          <div>
            <button
              type="submit"
              className="w-full py-2 px-4 bg-[#af4c4c] text-white font-semibold rounded-md shadow hover:bg-[#a04545] transition-all">
              Delete
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
              <p className="mt-4 text-gray-700 text-lg">Deleting Account...</p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default CustomerDelete;
