import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const VendorChangePw = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: "",
    currentPassword: "",
    newPassword: "",
  });

  const [responseMessage, setResponseMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleVendorChangePw = async (e) => {
    e.preventDefault();
    setResponseMessage("");
    setErrorMessage("");

    try {
      const response = await fetch(
        "http://localhost:8080/api/vendor/change-password",
        {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(formData), // Convert formData to JSON string
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error("Failed to Change password.");
      }

      // Parse JSON response to confirm success
      const data = await response.json();
      if (data.success) {
        setResponseMessage(`Password Changed Successfully!`);
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
        onClick={() => navigate("/issue-tickets")}>
        Back
      </button>
      <div className="w-full max-w-md bg-white p-6 rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">
          Vendor Change Password
        </h2>
        <form onSubmit={handleVendorChangePw} className="space-y-4">
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
              htmlFor="currentPassword"
              className="block text-sm font-medium text-gray-700">
              Current Password
            </label>
            <input
              type="password"
              id="currentPassword"
              name="currentPassword"
              value={formData.currentPassword}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-[#4CAF50] focus:border-[#4CAF50]"
              required
            />
          </div>

          <div>
            <label
              htmlFor="newPassword"
              className="block text-sm font-medium text-gray-700">
              New Password
            </label>
            <input
              type="password"
              id="newPassword"
              name="newPassword"
              value={formData.newPassword}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-[#4CAF50] focus:border-[#4CAF50]"
              required
            />
          </div>

          <div>
            <button
              type="submit"
              className="w-full py-2 px-4 bg-blue-500 text-white font-semibold rounded-md shadow hover:bg-blue-600 transition-all">
              Change password
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
      </div>
    </div>
  );
};

export default VendorChangePw;
