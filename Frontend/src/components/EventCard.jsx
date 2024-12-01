import React from 'react'

const EventCard = ({event}) => {
   return (
     <div className="bg-white shadow-lg rounded-lg p-4 flex flex-col items-center">
       <img
         src={event.image}
         alt="Event"
         className="w-full h-40 object-cover rounded-md mb-4"
       />
       <h3 className="text-lg font-bold mb-2">{event.name}</h3>
       <p className="text-gray-600 text-sm mb-1">
         <span className="font-semibold">Speaker: </span>
         {event.speaker}
       </p>
       <p className="text-gray-600 text-sm mb-3">
         <span className="font-semibold">Date & Location: </span>
         {event.dateLocation}
       </p>
       <button className="bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-600">
         Register Now
       </button>
     </div>
   );
}

export default EventCard
