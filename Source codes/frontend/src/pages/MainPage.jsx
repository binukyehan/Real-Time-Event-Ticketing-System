import React from "react";
import { useNavigate } from "react-router-dom";

const MainPage = () => {
  const navigate = useNavigate();

  const handleNavigation = (option) => {
    switch (option) {
      case "admin":
        navigate("./admin");
        break;
      case "vendor":
        navigate("./vendor-login");
        break;
      case "customer":
        navigate("./customer-login");
        break;
      default:
        break;
    }
  };

  return (
    <div className="flex flex-col justify-center items-center min-h-screen bg-blue-50 absolute inset-0 -z-10 h-full w-full [background:radial-gradient(125%_125%_at_50%_10%,#eff6ff_40%,#3a6ce0_100%)]">
      <h1 className="text-4xl font-bold text-[#333] mb-8">
        Event Ticketing System
      </h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <button
          className="w-full py-3 px-6 bg-[#4CAF50] text-white text-lg rounded-lg shadow-lg transform transition-all hover:bg-[#45a049] hover:scale-105"
          onClick={() => handleNavigation("admin")}>
          Admin
        </button>

        <button
          className="w-full py-3 px-6 bg-[#2196F3] text-white text-lg rounded-lg shadow-lg transform transition-all hover:bg-[#1976D2] hover:scale-105"
          onClick={() => handleNavigation("vendor")}>
          Vendor
        </button>

        <button
          className="w-full py-3 px-6 bg-[#FF9800] text-white text-lg rounded-lg shadow-lg transform transition-all hover:bg-[#FB8C00] hover:scale-105"
          onClick={() => handleNavigation("customer")}>
          Customer
        </button>
      </div>
    </div>
  );
};

export default MainPage;
