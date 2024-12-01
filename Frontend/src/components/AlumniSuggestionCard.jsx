import React, { useState } from 'react'
import { FaLinkedin } from "react-icons/fa6";
import { FaArrowRight } from "react-icons/fa";  

const AlumniSuggestionCard = ({ alumni,linkedin }) => {
  return (
    <div className="w-full p-4 border border-gray-300 rounded-lg shadow-md flex items-center space-x-4">
      <div className="w-12 h-12 bg-gray-300 rounded-full">
        <img src="" alt="" />
      </div>{" "}
      {/* Circle for profile image */}
      <div className="flex-1">
        <h2 className="text-lg font-semibold">{alumni.name}</h2>
        <p className="text-gray-500 text-sm">
          {alumni.companyName} | {alumni.yearsOfExperience} years
        </p>
        <div className="mt-2">
          <p className="text-gray-700">
            <span>Skills </span>:{" "}
            {alumni.skills
              ? alumni.skills.join(", ").length > 30
                ? `${alumni.skills.join(", ").slice(0, 30)}...`
                : alumni.skills.join(", ")
              : ""}
          </p>
        </div>
      </div>
      <div className="flex items-center flex-col space-y-4">
        {/* LinkedIn Button */}
        {linkedin && (
          <a
            href={`https://www.linkedin.com/in/${alumni.linkedin}`}
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-500 hover:text-blue-700 flex items-center space-x-2"
          >
            <span className="text-lg font-bold">Linkedin</span>
            <FaLinkedin size={24} />
          </a>
        )}

        {/* '>' Button */}
        <button
          className="text-blue-700 hover:text-red-700 flex items-center"
          onClick={() => alert("View More")}
        >
          <span className="text-base font-serif">See More Details</span>
          <FaArrowRight size={24} />
        </button>
      </div>
    </div>
  );
};

export default AlumniSuggestionCard;
