import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar'; 
import Login from './components/Login';
import RegisterPage from './components/Register'; 
import HotelList from './components/HotelList';
import HotelDetails from './components/HotelDetails';
import Feedback from './components/Feedback';
import MyBookings from './components/MyBookings'; 
import ReservationPage from './components/ReservationPage'; 
import Cookies from 'js-cookie';

function App() {
  const [token, setToken] = useState(null);

  useEffect(() => {
    const jwtToken = Cookies.get('jwtToken');
    if (jwtToken) {
      setToken(jwtToken);
    }
  }, []);

  const handleLogin = (jwtToken) => {
    setToken(jwtToken);
    Cookies.set('jwtToken', jwtToken, { expires: 1 });
  };

  const handleLogout = () => {
    setToken(null);
    Cookies.remove('jwtToken');
  };

  return (
    <Router>
      <div className="App">
        <Navbar token={token} handleLogout={handleLogout} /> {/* Adăugăm Navbar */}
        <Routes>
          <Route
            path="/login"
            element={<Login handleLogin={handleLogin} />}
          />
          <Route
            path="/register"
            element={<RegisterPage />}
          />
          <Route
            path="/mybookings"
            element={token ? <MyBookings /> : <Login handleLogin={handleLogin} />}
          />
          <Route
            path="/reservation"
            element={<ReservationPage />}
          />
          <Route
            path="/"
            element={token ? <HotelList handleLogout={handleLogout} /> : <Login handleLogin={handleLogin} />}
          />
          <Route path="/hotels/:hotelId" element={<HotelDetails />} />
          <Route path="/hotels/:hotelId/reservation" element={<ReservationPage />} />
          <Route path="/hotels/:hotelId/feedback" element={<Feedback />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
