import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Cookies from 'js-cookie';
import hotelImg from './img/hotelimg.png';
import { Slider, TextField, Button, Container, Grid, Card, CardContent, Typography } from '@mui/material';

function HotelList() {
  const [hotels, setHotels] = useState([]);
  const [distance, setDistance] = useState(300);
  const [searchDistance, setSearchDistance] = useState(300);

  useEffect(() => {
    const fetchHotels = async () => {
      try {
        const token = Cookies.get('jwtToken');
        const response = await fetch(`http://localhost:8080/api/getHotelsRange/${searchDistance}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        if (response.ok) {
          const data = await response.json();
          setHotels(data);
        } else {
          console.error('Failed to fetch hotels');
        }
      } catch (error) {
        console.error('Error fetching hotels:', error);
      }
    };
    fetchHotels();
  }, [searchDistance]);

  const handleSearch = () => {
    setSearchDistance(distance);
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  const handleDistanceChange = (e, newValue) => {
    const parsedValue = parseInt(newValue, 10);
    if (!isNaN(parsedValue)) {
      setDistance(parsedValue);
      setSearchDistance(parsedValue);
    }
  };

  return (
    <div style={{ minHeight: '100vh', backgroundColor: '#333', color: '#fff', paddingTop: '20px' }}>
      <Container maxWidth="md">
        <Typography variant="h4" align="center" gutterBottom>Hotel List</Typography>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={12} sm={6}>
            <Slider
              value={distance}
              min={100}
              max={500}
              step={100}
              onChange={handleDistanceChange}
              aria-labelledby="distance-slider"
            />
          </Grid>
          <Grid item xs={12} sm={3}>
          <TextField
  type="number"
  variant="outlined"
  size="small"
  value={distance}
  onChange={(e) => setDistance(e.target.value)}
  onKeyPress={handleKeyPress}
  inputProps={{ min: 100, max: 500, step: 100 }}
  fullWidth
  style={{ color: '#fff', borderRadius: '5px', border: '1px solid #fff' }}
  InputLabelProps={{ style: { color: '#fff' } }} 
  InputProps={{
    style: {
      color: '#fff' 
    }
  }}
/>
          </Grid>
          <Grid item xs={12} sm={3}>
            <Button variant="contained" onClick={handleSearch} fullWidth>Search</Button>
          </Grid>
        </Grid>
        <Grid container spacing={2} justifyContent="center">
          {hotels.map((hotel) => (
            <Grid item xs={12} sm={6} md={4} key={hotel.id}>
              <Card style={{ backgroundColor: '#444', color: '#fff', marginBottom: '20px' }}>
                <Link to={`/hotels/${hotel.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                  <CardContent>
                    <Grid container spacing={2} alignItems="center">
                      <Grid item xs={4}>
                        <img src={hotelImg} alt={hotel.name} style={{ width: '100%', height: 'auto', borderRadius: '5px' }} />
                      </Grid>
                      <Grid item xs={8}>
                        <Typography variant="h6" gutterBottom>{hotel.name}</Typography>
                        <Typography variant="body2">Location: {hotel.latitude}, {hotel.longitude}</Typography>
                      </Grid>
                    </Grid>
                  </CardContent>
                </Link>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Container>
    </div>
  );
}

export default HotelList;
