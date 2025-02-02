import React, { useState } from "react";
import EventList from "../../pages/EventList";

const DeleteEvent = () => {
  const [formData, setFormData] = useState({
    id: "",
    confirmation: "",
  });

  const [message, setmessage] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleConfigDelete = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/event/delete", {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        const message = await response.text();
        setmessage(message);
        setFormData({ id: "", confirmation: "" });
      } else {
        const errorInfo = await response.json();
        setmessage(
          `Error: ${errorInfo.message || "Failed to delete the event."}`
        );
      }
    } catch (error) {
      console.error("Exception ocurred: ", error);
      alert("An error ocurred while creating the event.");
    }
  };

  return (
    <div className="flex flex-col mt-0 min-h-screen items-center bg-blue-50">
      <EventList />
      <div className="p-4 max-w-md mx-auto bg-white rounded shadow-md ">
        <h2 className="text-xl font-bold mb-4">Delete Event</h2>
        {message && (
          <div
            className={`mb-4 p-2 rounded ${
              message.includes("success")
                ? "bg-green-200 text-green-800"
                : "bg-red-200 text-red-800"
            }`}>
            {message}
          </div>
        )}
        <form onSubmit={handleConfigDelete}>
          <label htmlFor="id" className="block mb-2 font-semibold">
            Event ID:
          </label>
          <input
            type="number"
            name="id"
            id="id"
            value={formData.id}
            onChange={handleInputChange}
            className="w-full p-2 border rounded mb-4"
            placeholder="Enter Event ID"
            required
          />
          <label htmlFor="confirmation" className="block mb-2 font-semibold">
            Type "DELETE" to confirm the deleting of event:
          </label>
          <input
            type="text"
            name="confirmation"
            id="confirmation"
            value={formData.confirmation}
            onChange={handleInputChange}
            className="w-full p-2 border rounded mb-4"
            placeholder="Type 'DELETE' to confirm deletion"
            required
          />
          <button
            type="submit"
            className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 mr-2">
            Delete Event
          </button>
          <button
            type="button"
            onClick={() => window.history.back()}
            className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600">
            Cancel
          </button>
        </form>
      </div>
    </div>
  );
};

export default DeleteEvent;
