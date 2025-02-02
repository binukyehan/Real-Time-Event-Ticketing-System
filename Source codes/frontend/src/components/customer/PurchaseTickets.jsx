import React, { useState } from "react";
import EventList from "../../pages/EventList";
import { useNavigate } from "react-router-dom";

const PurchaseTickets = () => {
  const [formData, setFormData] = useState({
    name: "",
    eventId: 0,
    numberOfTickets: 0,
  });

  const [responseMessage, setResponseMessage] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const [dropdownOpen, setDropdownOpen] = useState(false);

  const navigate = useNavigate();

  const toggleDropdown = () => {
    //toggle the dropdown menu
    setDropdownOpen(!dropdownOpen);
  };

  const handleOptionClick = (option) => {
    //handle the pathing depending on the option selected
    if (option === "delete") {
      navigate("/customer-delete-acc");
    } else if (option === "change") {
      navigate("/customer-change-pw");
    }
  };

  const handleTicketPurchase = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(
        "http://localhost:8080/api/customer/purchase-ticket",
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(formData),
        }
      );

      const message = await response.text();
      if (response.ok) {
        setResponseMessage(message);
        setFormData({ name: "", eventId: 0, numberOfTickets: 0 }); //reset the input fields
      } else {
        setResponseMessage(`An error occurred: ${message}`);
      }
    } catch (error) {
      setResponseMessage("An error occurred while issuing tickets.");
    }
  };

  return (
    <div className="flex flex-col items-center mt-0 min-h-screen bg-blue-50">
      <button
        className="absolute top-8 left-10 py-2 px-4 bg-blue-500 text-white font-semibold rounded shadow-lg hover:bg-blue-600 transition-all hover:scale-105"
        onClick={() => navigate("/")}>
        Home
      </button>
      <EventList />
      <h2 className="text-2xl mt-20 font-bold mb-4">Purchase Tickets</h2>
      <form
        className="w-1/4 p-4 border rounded-md shadow bg-white"
        onSubmit={handleTicketPurchase}>
        <div className="mb-4">
          <label htmlFor="username" className="block font-medium mb-1">
            Username:{" "}
          </label>
          <input
            type="text"
            id="name"
            name="name"
            className="w-full px-4 py-2 border rounded"
            value={formData.name}
            onChange={handleInputChange}
            required
          />
        </div>
        <dev className="mb-4">
          <label htmlFor="eventId" className="block font-medium mb-1">
            Event ID:{" "}
          </label>
          <input
            type="number"
            id="eventId"
            name="eventId"
            className="w-full px-4 py-2 border rounded"
            value={formData.eventId}
            onChange={handleInputChange}
            required
          />
        </dev>
        <div className="mb-4">
          <label htmlFor="numberOfTickets" className="block font-medium mb-1">
            Number of Tickets:
          </label>
          <input
            type="number"
            id="numberOfTickets"
            name="numberOfTickets"
            className="w-full px-4 py-2 border rounded"
            value={formData.numberOfTickets}
            onChange={handleInputChange}
            required
          />
        </div>
        <button
          type="submit"
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
          Purchase Ticket
        </button>
      </form>

      {responseMessage && (
        <div
          className={`mt-4 p-4 border rounded ${
            responseMessage.includes("successfully")
              ? "bg-green-200 text-green-800"
              : "bg-red-200 text-red-800"
          }`}>
          <p>{responseMessage}</p>
        </div>
      )}

      <div className="mt-20 text-center pb-40">
        <div className="relative inline-block">
          <button
            onClick={toggleDropdown}
            className="py-2 px-4 bg-blue-500 text-white font-semibold rounded-md shadow hover:bg-blue-600 transition-all">
            Other Options
          </button>
          {dropdownOpen && (
            <div className="relative mt-2 w-48 bg-white border border-gray-300 rounded-md shadow-lg z-10">
              <button
                onClick={() => handleOptionClick("delete")}
                className="block w-full text-left px-4 py-2 text-gray-700 hover:bg-gray-100">
                Delete Account
              </button>
              <button
                onClick={() => handleOptionClick("change")}
                className="block w-full text-left px-4 py-2 text-gray-700 hover:bg-gray-100">
                Change Password
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default PurchaseTickets;
