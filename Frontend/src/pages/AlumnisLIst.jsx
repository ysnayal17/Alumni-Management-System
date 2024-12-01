import React from 'react'
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import AlumniTabList from '../components/AlumniTabList';

const AlumnisLIst = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <Navbar />
      <div className="flex-grow">
        <AlumniTabList />
      </div>
      <Footer />
    </div>
  );
}

export default AlumnisLIst
