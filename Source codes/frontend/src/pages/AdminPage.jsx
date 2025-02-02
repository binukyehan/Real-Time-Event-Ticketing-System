import React from "react";
import { useNavigate } from "react-router-dom";

const AdminPage = () => {
  const navigate = useNavigate();

  const handleNavigation = (option) => {
    switch (option) {
      case "create":
        navigate("/create-event");
        break;
      case "delete":
        navigate("/delete-event");
        break;
      case "status":
        navigate("/control-panel");
      default:
        break;
    }
  };

  return (
    <div className="flex flex-col justify-center items-center min-h-screen bg-blue-50">
      <button
        className="absolute top-8 left-10 py-2 px-4 bg-blue-500 text-white font-semibold rounded shadow-lg hover:bg-blue-600 transition-all hover:scale-105"
        onClick={() => navigate("/")}>
        Back
      </button>
      <h1 className="text-4xl font-bold text-[#333] mb-8">Admin Page</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <button
          className="w-full py-3 px-6 bg-blue-500 text-white text-lg rounded-lg shadow-lg transform transition-all hover:bg-blue-600 hover:scale-105"
          onClick={() => handleNavigation("status")}>
          Control Events
        </button>

        <button
          className="w-full py-3 px-6 bg-green-500 text-white text-lg rounded-lg shadow-lg transform transition-all hover:bg-green-600 hover:scale-105"
          onClick={() => handleNavigation("create")}>
          Create Event
        </button>

        <button
          className="w-full py-3 px-6 bg-red-500 text-white text-lg rounded-lg shadow-lg transform transition-all hover:bg-red-600 hover:scale-105"
          onClick={() => handleNavigation("delete")}>
          Delete Event
        </button>
      </div>
    </div>
  );
};

export default AdminPage;
