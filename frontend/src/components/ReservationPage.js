import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';

import Cookies from 'js-cookie';

function ReservationPage() {
  const [checkInDate, setCheckInDate] = useState('');
  const [checkOutDate, setCheckOutDate] = useState('');
  const [error, setError] = useState('');
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const hotelId = searchParams.get('hotelId');
  const roomId = searchParams.get('roomId');
  const navigate = useNavigate();

  useEffect(() => {
  }, []);

  const handleCheckInDateChange = (event) => {
    setCheckInDate(event.target.value);
  };

  const handleCheckOutDateChange = (event) => {
    setCheckOutDate(event.target.value);
  };

  const handleReservation = async () => {
    try {
      const token = Cookies.get('jwtToken');
      const response = await fetch('http://localhost:8080/api/bookRoom', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          idUser: 1,
          roomNumber: roomId,
          from: checkInDate,
          to: checkOutDate,
          hotelID: hotelId
        })
      });
  
      if (!response.ok) {
        throw new Error('Failed to make reservation');
      }
  
      console.log('Reservation request sent!');
      navigate('/');
    } catch (error) {
      setError(error.message);
    }
  };
  
  return (
    <div style={{ maxWidth: '800px', margin: 'auto', padding: '20px' }}>
      <h2 style={{ fontSize: '24px', marginBottom: '20px' }}>Reservation</h2>
      <div style={{ marginBottom: '15px' }}>
        <label htmlFor="checkInDate">Check-in Date:</label>
        <input
          type="date"
          id="checkInDate"
          value={checkInDate}
          onChange={handleCheckInDateChange}
          style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ccc' }}
        />
      </div>
      <div style={{ marginBottom: '15px' }}>
        <label htmlFor="checkOutDate">Check-out Date:</label>
        <input
          type="date"
          id="checkOutDate"
          value={checkOutDate}
          onChange={handleCheckOutDateChange}
          style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ccc' }}
        />
      </div>
      <button
        onClick={handleReservation}
        style={{ backgroundColor: '#007bff', color: '#fff', border: 'none', borderRadius: '4px', padding: '10px 20px', cursor: 'pointer', width: '100%' }}
      >
        Rent
      </button>
      {error && <p style={{ color: 'red', marginTop: '10px' }}>{error}</p>}
      <Link to={`/hotels/${hotelId}`} style={{ display: 'block', textAlign: 'center', marginTop: '20px', color: '#007bff', textDecoration: 'none' }}>Back to Hotel Details</Link>
      <p>Selected Hotel ID: {hotelId}</p>
      <p>Selected Room ID: {roomId}</p>
    </div>
  );
}

export default ReservationPage;
