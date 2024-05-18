
import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom'; 
import Cookies from 'js-cookie';

function HotelDetails() {
  const { hotelId } = useParams();
  const [hotel, setHotel] = useState(null);
  const [token, setToken] = useState('');

  useEffect(() => {
    const fetchHotelDetails = async () => {
      try {
        const tokenFromCookie = Cookies.get('jwtToken');
        setToken(tokenFromCookie);

        const response = await fetch(`http://localhost:8080/api/getHotel/${hotelId}`, {
          headers: {
            'Authorization': `Bearer ${tokenFromCookie}`
          }
        });

        if (response.ok) {
          const data = await response.json();
          setHotel(data);
        } else {
          console.error('Failed to fetch hotel details');
        }
      } catch (error) {
        console.error('Error fetching hotel details:', error);
      }
    };
    fetchHotelDetails();
  }, [hotelId]);

  return (
    <div style={{ maxWidth: '800px', margin: 'auto', padding: '20px' }}>
      <h2 style={{ fontSize: '24px', marginBottom: '20px' }}>Hotel Details</h2>
      {hotel && (
        <div style={{ backgroundColor: '#f9f9f9', padding: '20px', borderRadius: '8px' }}>
          <h3 style={{ fontSize: '20px', marginBottom: '10px' }}>{hotel.name}</h3>
          <p style={{ marginBottom: '10px' }}>Location: {hotel.latitude}, {hotel.longitude}</p>
          <p style={{ fontWeight: 'bold', marginBottom: '10px' }}>Rooms:</p>
          <ul style={{ listStyleType: 'none', padding: '0' }}>
            {hotel.rooms.map((room, index) => (
              <li key={index} style={{ marginBottom: '10px', display: 'flex', alignItems: 'center' }}>
                <span style={{ fontWeight: 'bold', marginRight: '10px', flex: '1' }}>Room {room.roomNumber}</span>
                <span style={{ flex: '1', marginRight: '10px' }}>Type: {room.type}, Price: {room.price}</span>
                {room.available ? (
                  <Link to={`/hotels/${hotelId}/reservation?roomId=${room.roomNumber}&hotelId=${hotelId}`} style={{ padding: '8px 16px', backgroundColor: '#007bff', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', textDecoration: 'none' }}>Rent</Link>
                ) : (
                  <span style={{ color: '#dc3545', marginLeft: '10px' }}>Not available</span>
                )}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}

export default HotelDetails;
