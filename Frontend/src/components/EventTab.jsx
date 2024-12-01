import React, { useState } from 'react'
import EventCard from './EventCard';

const EventTab = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 9;

  const events = Array.from({ length: 20 }, (_, index) => ({
    id: index + 1,
    image: "https://via.placeholder.com/150",
    name: `Event ${index + 1}`,
    speaker: `Speaker ${index + 1}`,
    dateLocation: `Date ${index + 1} | Location ${index + 1}`,
  }));

  // Filter events based on search query
  const filteredEvents = events.filter(
    (event) =>
      event.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      event.speaker.toLowerCase().includes(searchQuery.toLowerCase())
  );

  // Pagination logic
  const indexOfLastEvent = currentPage * itemsPerPage;
  const indexOfFirstEvent = indexOfLastEvent - itemsPerPage;
  const currentEvents = filteredEvents.slice(
    indexOfFirstEvent,
    indexOfLastEvent
  );
  const totalPages = Math.ceil(filteredEvents.length / itemsPerPage);

  return (
    <div className="p-6  min-h-screen flex flex-col">
      {/* Search Bar */}
      <div className="mb-6">
        <input
          type="text"
          placeholder="Search by event name or speaker..."
          className="w-full p-3 border border-gray-300 rounded-lg"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
      </div>

      {/* Event Cards */}
      <div className="flex-grow">
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {currentEvents.map((event) => (
            <EventCard key={event.id} event={event} />
          ))}
        </div>
      </div>

      {/* Pagination Controls */}
      <div className="flex justify-center mt-6 mb-6">
        <button
          className="px-4 py-2 bg-blue-500 text-white rounded-l-lg hover:bg-blue-600 disabled:bg-gray-300"
          disabled={currentPage === 1}
          onClick={() => setCurrentPage((prev) => prev - 1)}
        >
          Prev
        </button>
        <span className="px-4 py-2 text-gray-700">{`Page ${currentPage} of ${totalPages}`}</span>
        <button
          className="px-4 py-2 bg-blue-500 text-white rounded-r-lg hover:bg-blue-600 disabled:bg-gray-300"
          disabled={currentPage === totalPages}
          onClick={() => setCurrentPage((prev) => prev + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
}

export default EventTab
