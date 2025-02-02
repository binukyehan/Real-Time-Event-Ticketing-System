import React, { useState } from "react";

const CreateEvent = () => {
  const [formData, setFormData] = useState({
    eventName: "",
    eventLocation: "",
    eventDate: "",
    maxTicketCapacity: "",
    totalTickets: "",
    ticketReleaseRate: "",
    customerRetrievalRate: "",
  });

  const [message, setmessage] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({ ...prevState, [name]: value }));
  };

  const handleConfigSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/event/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });
      if (response.ok) {
        const message = await response.text();
        setmessage(message);
        setFormData({
          eventName: "",
          eventLocation: "",
          eventDate: "",
          maxTicketCapacity: "",
          totalTickets: "",
          ticketReleaseRate: "",
          customerRetrievalRate: "",
        });
      } else {
        const errorInfo = await response.json();
        setmessage(
          `Error: ${errorInfo.message || "Failed to create the event."}`
        );
      }
    } catch (error) {
      console.error("Exception ocurred: ", error);
      alert("An error ocurred while creating the event.");
    }
  };

  return (
    <div className=" min-h-screen bg-blue-50 p-4">
      <div className="max-w-md mx-auto mt-20 mb-10 p-6 rounded bg-white shadow-md">
        <h1 className="text-2xl font-bold mb-4 text-center">Create Event</h1>
        <form onSubmit={handleConfigSubmit}>
          <div className="mb-4">
            <label className="block text-gray-700 font-medium mb-2">
              Event Name
            </label>
            <input
              type="text"
              name="eventName"
              value={formData.eventName}
              onChange={handleInputChange}
              className="w-full p-2 border rounded"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 font-medium mb-2">
              Location
            </label>
            <input
              type="text"
              name="eventLocation"
              value={formData.eventLocation}
              onChange={handleInputChange}
              className="w-full p-2 border rounded"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 font-medium mb-2">Date</label>
            <input
              type="date"
              name="eventDate"
              value={formData.eventDate}
              onChange={handleInputChange}
              className="w-full p-2 border rounded"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 font-medium mb-2">
              Max Ticket Capacity
            </label>
            <input
              type="number"
              name="maxTicketCapacity"
              value={formData.maxTicketCapacity}
              onChange={handleInputChange}
              className="w-full p-2 border rounded"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 font-medium mb-2">
              Total Tickets
            </label>
            <input
              type="number"
              name="totalTickets"
              value={formData.totalTickets}
              onChange={handleInputChange}
              className="w-full p-2 border rounded"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 font-medium mb-2">
              Ticket Release Rate
            </label>
            <input
              type="number"
              name="ticketReleaseRate"
              value={formData.ticketReleaseRate}
              onChange={handleInputChange}
              className="w-full p-2 border rounded"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 font-medium mb-2">
              Customer Retrieval Rate
            </label>
            <input
              type="number"
              name="customerRetrievalRate"
              value={formData.customerRetrievalRate}
              onChange={handleInputChange}
              className="w-full p-2 border rounded"
              required
            />
          </div>
          {message && (
            <div
              className={`mb-4 p-2 rounded ${
                message.includes("created")
                  ? "bg-green-200 text-green-800"
                  : "bg-red-200 text-red-800"
              }`}>
              {message}
            </div>
          )}
          <div className="flex justify-between mt-4 space-x-8">
            <button
              type="button" //todo add onlcick.
              onClick={() => window.history.back()}
              className="w-full bg-gray-500 text-white p-2 rounded hover:bg-gray-600">
              Cancel
            </button>
            <button
              type="submit"
              className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600">
              Create Event
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateEvent;
