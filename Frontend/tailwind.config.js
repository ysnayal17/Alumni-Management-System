/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        sans: ["Poppins"],
      },
      backgroundImage: {
        "custom-gradient":
          "linear-gradient(90deg, rgba(19, 75, 187, 1) 0%, rgba(76, 23, 107, 1) 100%)",
      },
    },
  },
  plugins: [],
};
