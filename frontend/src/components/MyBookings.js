import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Cookies from 'js-cookie';

function MyBookings() {
  
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const token = Cookies.get('jwtToken');

        const response = await fetch('http://localhost:8080/api/bookings', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        if (response.ok) {
          const data = await response.json();
          setBookings(data);
        } else {
          console.error('Failed to fetch bookings');
        }
      } catch (error) {
        console.error('Error fetching bookings:', error);
      }
    };


    fetchBookings();
  }, []);

  return (
    <div style={{ minHeight: '100vh', backgroundColor: '#f0f0f0', padding: '20px' }}>
      <h2 style={{ textAlign: 'center', marginBottom: '20px' }}>My Bookings</h2>
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        {}
        {bookings.length > 0 ? (
          bookings.map(booking => (
            <div key={booking.id} style={{ border: '1px solid #ccc', borderRadius: '5px', padding: '10px', marginBottom: '10px', width: '80%' }}>
              <h3>{booking.hotelName}</h3>
              <p>Check-in: {booking.checkInDate}</p>
              <p>Check-out: {booking.checkOutDate}</p>
              <p>Room Type: {booking.roomType}</p>
              <p>Total Price: ${booking.totalPrice}</p>
            </div>
          ))
        ) : (
          <p style={{ textAlign: 'center', fontSize: '18px' }}>You have no bookings yet. <Link to="/">Explore Hotels</Link></p>
        )}
      </div>
    </div>
  );
}

export default MyBookings;
