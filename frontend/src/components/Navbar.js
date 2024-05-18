import React from 'react';
import { Link } from 'react-router-dom';

function Navbar() {
  return (
    <nav style={{ backgroundColor: '#333', color: '#fff', padding: '10px' }}>
      <div style={{ maxWidth: '800px', margin: 'auto', display: 'flex', justifyContent: 'space-between' }}>
        <Link to="/" style={{ textDecoration: 'none', color: '#fff' }}>Hotel List</Link>
        <Link to="/mybookings" style={{ textDecoration: 'none', color: '#fff' }}>My Bookings</Link>
        <div>
          <Link to="/login" style={{ textDecoration: 'none', color: '#fff', marginRight: '10px' }}>Login</Link>
          <Link to="/register" style={{ textDecoration: 'none', color: '#fff', marginRight: '10px' }}>Register</Link>
          
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
