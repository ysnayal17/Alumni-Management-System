import React, { useState, useEffect, useRef } from "react";
import AlumniSuggestionCard from "./AlumniSuggestionCard";

const AlumniTabList = () => {
  const [alumniData, setAlumniData] = useState([
   
  { id: 1, name: 'John Doe', department: 'CE', companyName: 'Company A', yearsOfExperience: 3, skills: ['JavaScript', 'React'] },
  { id: 2, name: 'Jane Smith', department: 'IT', companyName: 'Company B', yearsOfExperience: 5, skills: ['Python', 'Django'] },
  { id: 3, name: 'Michael Johnson', department: 'MH', companyName: 'Company C', yearsOfExperience: 2, skills: ['Java', 'Spring'] },
  { id: 4, name: 'Emily Davis', department: 'CE', companyName: 'Company D', yearsOfExperience: 6, skills: ['C++', 'Data Structures'] },
//   { id: 5, name: 'Chris Brown', department: 'IT', companyName: 'Company E', yearsOfExperience: 4, skills: ['PHP', 'Laravel'] },
//   { id: 6, name: 'David Wilson', department: 'CE', companyName: 'Company F', yearsOfExperience: 7, skills: ['JavaScript', 'Node.js'] },
//   { id: 7, name: 'Sophia Garcia', department: 'MH', companyName: 'Company G', yearsOfExperience: 8, skills: ['C#', '.NET'] },
//   { id: 8, name: 'James Lee', department: 'CE', companyName: 'Company H', yearsOfExperience: 2, skills: ['React', 'GraphQL'] },
//   { id: 9, name: 'Olivia Martinez', department: 'IT', companyName: 'Company I', yearsOfExperience: 4, skills: ['Ruby', 'Rails'] },
//   { id: 10, name: 'Liam Gonzalez', department: 'MH', companyName: 'Company J', yearsOfExperience: 3, skills: ['SQL', 'MySQL'] },
//   { id: 11, name: 'Ava Perez', department: 'CE', companyName: 'Company K', yearsOfExperience: 6, skills: ['Java', 'Spring Boot'] },
//   { id: 12, name: 'Ethan Taylor', department: 'IT', companyName: 'Company L', yearsOfExperience: 7, skills: ['Python', 'Flask'] },
//   { id: 13, name: 'Mia Anderson', department: 'MH', companyName: 'Company M', yearsOfExperience: 9, skills: ['JavaScript', 'Angular'] },
//   { id: 14, name: 'Matthew Thomas', department: 'CE', companyName: 'Company N', yearsOfExperience: 5, skills: ['C', 'Embedded Systems'] },
//   { id: 15, name: 'Isabella Moore', department: 'IT', companyName: 'Company O', yearsOfExperience: 4, skills: ['JavaScript', 'Node.js'] },
//   { id: 16, name: 'Lucas Jackson', department: 'CE', companyName: 'Company P', yearsOfExperience: 8, skills: ['Go', 'Kubernetes'] },
//   { id: 17, name: 'Charlotte White', department: 'MH', companyName: 'Company Q', yearsOfExperience: 5, skills: ['Swift', 'iOS Development'] },
//   { id: 18, name: 'Amelia Harris', department: 'CE', companyName: 'Company R', yearsOfExperience: 3, skills: ['PHP', 'WordPress'] },
//   { id: 19, name: 'Harper Clark', department: 'IT', companyName: 'Company S', yearsOfExperience: 7, skills: ['Java', 'Spring'] },
//   { id: 20, name: 'Jack Lewis', department: 'MH', companyName: 'Company T', yearsOfExperience: 4, skills: ['JavaScript', 'Vue.js'] },
//   { id: 21, name: 'Aria Young', department: 'CE', companyName: 'Company U', yearsOfExperience: 2, skills: ['TypeScript', 'React'] },
//   { id: 22, name: 'Sebastian King', department: 'IT', companyName: 'Company V', yearsOfExperience: 6, skills: ['Django', 'Rest APIs'] },
//   { id: 23, name: 'Zoe Scott', department: 'MH', companyName: 'Company W', yearsOfExperience: 3, skills: ['Flutter', 'Mobile Development'] },
//   { id: 24, name: 'Benjamin Adams', department: 'CE', companyName: 'Company X', yearsOfExperience: 9, skills: ['Python', 'Machine Learning'] },
//   { id: 25, name: 'Ella Nelson', department: 'IT', companyName: 'Company Y', yearsOfExperience: 10, skills: ['JavaScript', 'React', 'Node.js'] },

  ]);
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedDepartment, setSelectedDepartment] = useState("");
  const [filteredData, setFilteredData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false); // Toggle for dropdown


  const dropdownRef = useRef(null);

  useEffect(() => {
    // Close the dropdown when clicking outside of it
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setDropdownOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

//   useEffect(() => {
//     // Replace with your actual API endpoint to fetch alumni data
//     fetch("https://api.example.com/alumni")
//       .then((response) => response.json())
//       .then((data) => setAlumniData(data));
//   }, []);

  // Filter alumni data based on search query and selected department
  useEffect(() => {
    const filtered = alumniData.filter((alumni) => {
      const matchesSearch = alumni.name
        .toLowerCase()
        .includes(searchQuery.toLowerCase());
      const matchesDepartment = selectedDepartment
        ? alumni.department === selectedDepartment
        : true;
      return matchesSearch && matchesDepartment;
    });
    setFilteredData(filtered);
  }, [alumniData, searchQuery, selectedDepartment]);

  // Pagination logic
  const indexOfLastAlumni = currentPage * itemsPerPage;
  const indexOfFirstAlumni = indexOfLastAlumni - itemsPerPage;
  const currentAlumni = filteredData.slice(
    indexOfFirstAlumni,
    indexOfLastAlumni
  );

  // Calculate total pages
  const totalPages = Math.ceil(filteredData.length / itemsPerPage);

  return (
    <div className="p-6 flex flex-col min-h-screen">
      {/* Search and Filter Section */}
      <div className="flex items-center space-x-4 mb-6">
        <input
          type="text"
          className="p-2 border border-gray-300 rounded-lg w-full md:w-1/3"
          placeholder="Search alumni..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />

        {/* Dropdown Filter for Department */}
        <div className="relative" ref={dropdownRef}>
          <button
            onClick={() => setIsDropdownOpen(!isDropdownOpen)}
            className="p-2 border border-gray-300 rounded-lg w-full md:w-48"
          >
            {selectedDepartment || "Select Department"}
          </button>

          {/* Dropdown Content */}
          {isDropdownOpen && (
            <div className="absolute right-0 mt-2 w-full max-w-xs bg-white border border-gray-300 rounded-lg shadow-lg z-10">
              <ul>
                {["CE", "IT", "MH"].map((dept) => (
                  <li
                    key={dept}
                    className="px-4 py-2 hover:bg-gray-200 cursor-pointer"
                    onClick={() => {
                      setSelectedDepartment(dept);
                      setIsDropdownOpen(false); // Close dropdown after selection
                    }}
                  >
                    {dept}
                  </li>
                ))}
                <li
                  className="px-4 py-2 hover:bg-gray-200 cursor-pointer"
                  onClick={() => {
                    setSelectedDepartment("");
                    setIsDropdownOpen(false); // Clear filter and close dropdown
                  }}
                >
                  Clear Filter
                </li>
              </ul>
            </div>
          )}
        </div>
      </div>

      {/* Alumni Cards */}
          <div className="flex-grow">

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-2 gap-6 mb-6">
        {currentAlumni.map((alumni) => (
            <AlumniSuggestionCard key={alumni.id} alumni={alumni} linkedin={false}/>
        ))}
      </div>
        </div>

      {/* Pagination Controls */}
      <div className="flex justify-center mt-6 mb-4">
        <button
          className="px-4 py-2 bg-blue-500 text-white rounded-l-lg"
          disabled={currentPage === 1}
          onClick={() => setCurrentPage(currentPage - 1)}
        >
          Prev
        </button>

        <span className="px-4 py-2">{`Page ${currentPage} of ${totalPages}`}</span>

        <button
          className="px-4 py-2 bg-blue-500 text-white rounded-r-lg"
          disabled={currentPage === totalPages}
          onClick={() => setCurrentPage(currentPage + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default AlumniTabList;
