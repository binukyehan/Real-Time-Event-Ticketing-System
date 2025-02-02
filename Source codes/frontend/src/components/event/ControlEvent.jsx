import React, { useState } from "react";
import EventList from "../../pages/EventList";
import { useNavigate } from "react-router-dom";

const ControlPanel = () => {
  const [eventId, setEventId] = useState("");
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const handleControlEvent = async (status) => {
    if (!eventId) {
      setMessage("Please enter a valid Event ID.");
      return;
    }

    try {
      const response = await fetch(
        "http://localhost:8080/api/event/control-panel",
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            id: eventId,
            eventStatus: status,
          }),
        }
      );

      if (response.ok) {
        const result = await response.text(); //backend sends a String response
        setMessage(result);
      } else {
        const error = await response.text();
        setMessage(`Error: ${error}`);
      }
    } catch (error) {
      console.error("Error controlling event status:", error);
      setMessage("Failed to update the event status. Please try again.");
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-blue-50">
      <button
        className="absolute top-8 left-10 py-2 px-4 bg-blue-500 text-white font-semibold rounded shadow-lg hover:bg-blue-600 transition-all hover:scale-105"
        onClick={() => navigate("/")}>
        Home
      </button>
      <EventList />
      <h1 className="text-2xl font-bold mb-6">Control Panel</h1>
      <div className="flex flex-col items-center bg-white p-6 rounded shadow-lg space-y-4 w-full max-w-md">
        <label className="text-lg font-medium">
          Enter Event ID:
          <input
            type="text"
            value={eventId}
            onChange={(e) => setEventId(e.target.value)}
            className="mt-2 p-2 w-full border border-gray-300 rounded"
            placeholder="Event ID"
          />
        </label>
        <div className="flex space-x-4">
          <button
            onClick={() => handleControlEvent(true)}
            className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600">
            Start
          </button>
          <button
            onClick={() => handleControlEvent(false)}
            className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600">
            Stop
          </button>
        </div>
        {message && (
          <p className="text-center text-sm mt-4 text-gray-700">{message}</p>
        )}
      </div>
    </div>
  );
};

export default ControlPanel;
