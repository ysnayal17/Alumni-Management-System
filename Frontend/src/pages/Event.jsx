import React from 'react'
import Footer from '../components/Footer';
import Navbar from '../components/Navbar';
import EventTab from '../components/EventTab';

const Event = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <Navbar />
      <div className="flex-grow">
        <EventTab />
      </div>
      <Footer />
    </div>
  );
}

export default Event
