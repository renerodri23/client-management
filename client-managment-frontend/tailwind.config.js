/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          DEFAULT: '#1d4ed8',
          strong: '#1e40af',
          medium: '#3b82f6',
          soft: '#dbeafe',
        }
      }
    },
  },
  plugins: [],
}