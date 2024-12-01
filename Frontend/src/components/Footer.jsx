import React from 'react'

const Footer = () => {
     const currentYear = new Date().getFullYear();
  return (
<>
 <footer className="bg-gray-800 text-gray-300 py-4">
      <div className="container mx-auto px-4 flex flex-col md:flex-row justify-between items-center">
        {/* Left Section: Logo or System Name */}
        <div className="text-center md:text-left">
          <h1 className="text-lg font-bold text-white">Alumni Management System</h1>
          <p className="text-sm text-gray-400">Connecting Alumni, Building Futures</p>
        </div>

        {/* Right Section: Copyright */}
        <div className="mt-4 md:mt-0 text-center md:text-right">
          <p className="text-sm">
            &copy; {currentYear} Alumni Management System. All rights reserved.
          </p>
        </div>
      </div>
      </footer>
</>
  )
}

export default Footer
