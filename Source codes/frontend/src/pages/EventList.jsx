import React, { useEffect, useState } from "react";

const EventList = () => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    const eventSource = new EventSource(
      "http://localhost:8080/api/event/stream"
    );

    eventSource.onmessage = (event) => {
      setEvents(JSON.parse(event.data));
    };

    eventSource.onerror = (error) => {
      console.error("EventSource failed:", error);
    };

    return () => {
      eventSource.close();
    };
  }, []);

  return (
    <div className="flex flex-col items-center justify-center py-10 bg-blue-50">
      <h1 className="text-2xl font-bold mb-6 text-center">Available Events</h1>
      <div className="flex w-full max-w-6xl overflow-x-auto px-4 py-2">
        <div className="flex justify-center items-center space-x-4 whitespace-nowrap">
          {events.map((event) => (
            <div
              key={event.eventId}
              className="relative min-w-[250px] p-4 rounded-lg shadow-lg text-white bg-blue-400 flex flex-col items-center overflow-hidden">
              <h3 className="text-2xl font-semibold text-blue-900">
                {event.eventName}
              </h3>
              <p className="text-lg text-blue-900">Event ID: {event.eventId}</p>
              <p className="mt-4 text-lg">Tickets Available</p>
              <p className="mt-2 h-11 w-11 flex items-center justify-center rounded-full bg-blue-100 text-blue-900 text-lg font-bold shadow-lg">
                {event.currentNoOfTickets}
              </p>
              <div
                className={`absolute bottom-2 right-2 h-4 w-4 rounded-full ${
                  event.eventStatus ? "bg-green-500" : "bg-red-500"
                }`}></div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default EventList;
