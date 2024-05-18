import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

function RegisterPage() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [confirmEmail, setConfirmEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [gender, setGender] = useState('');
  const [country, setCountry] = useState('');
  const [city, setCity] = useState('');
  const [address, setAddress] = useState('');
  const [zipCode, setZipCode] = useState('');
  const [error, setError] = useState('');
  const [avatar, setAvatar] = useState(null);
  const [avatarError, setAvatarError] = useState('');
  const navigate = useNavigate();

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handleConfirmEmailChange = (event) => {
    setConfirmEmail(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
  };

  const handleFirstNameChange = (event) => {
    setFirstName(event.target.value);
  };

  const handleLastNameChange = (event) => {
    setLastName(event.target.value);
  };

  const handlePhoneNumberChange = (event) => {
    setPhoneNumber(event.target.value);
  };

  const handleGenderChange = (event) => {
    setGender(event.target.value);
  };

  const handleCountryChange = (event) => {
    setCountry(event.target.value);
  };

  const handleCityChange = (event) => {
    setCity(event.target.value);
  };

  const handleAddressChange = (event) => {
    setAddress(event.target.value);
  };

  const handleZipCodeChange = (event) => {
    setZipCode(event.target.value);
  };

  const handleAvatarChange = (event) => {
    const selectedFile = event.target.files[0];
    if (selectedFile) {
      if (selectedFile.size > 1024 * 1024) { 
        setAvatarError('File size exceeds 1MB limit');
        return;
      }
  
      const reader = new FileReader();
      reader.onloadend = () => {

        const img = new Image();
        img.src = reader.result;
  
        
        img.onload = function() {
          
          const canvas = document.createElement('canvas');
          const ctx = canvas.getContext('2d');
  
          
          const maxWidth = 300; 
          const maxHeight = 300; 
          let width = img.width;
          let height = img.height;
  
          if (width > height) {
            if (width > maxWidth) {
              height *= maxWidth / width;
              width = maxWidth;
            }
          } else {
            if (height > maxHeight) {
              width *= maxHeight / height;
              height = maxHeight;
            }
          }
  
          canvas.width = width;
          canvas.height = height;
  
          
          ctx.drawImage(img, 0, 0, width, height);
  
          
          const compressedDataUrl = canvas.toDataURL('image/jpeg', 0.7); 
  
          
          setAvatar(compressedDataUrl);
          setAvatarError('');
        };
      };
      reader.readAsDataURL(selectedFile);
    }
  };
  

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      if (email !== confirmEmail) {
        setError('Emails do not match');
        return;
      }
      if (password !== confirmPassword) {
        setError('Passwords do not match');
        return;
      }

      const response = await fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username,
          email,
          password,
          firstName,
          lastName,
          role: 'MEMBER',
          phoneNumber,
          gender,
          address: {
            country,
            city,
            address,
            zipCode
          },
          avatarPhoto:avatar
        }),
      });

      if (response.ok) {
        console.log('Registration successful');
        navigate('/login');
      } else {
        const data = await response.json();
        setError(data.message);
      }
    } catch (error) {
      console.error('Error during registration:', error);
      setError('An unexpected error occurred. Please try again later.');
    }
  };

  return (
    <div style={{ minHeight: '100vh', backgroundColor: 'black', color: 'white', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
      <form onSubmit={handleSubmit} style={{ width: '300px' }}>
        <h2 style={{ textAlign: 'center', marginBottom: '20px' }}>Register</h2>
        {error && <div style={{ color: 'red', marginBottom: '10px' }}>{error}</div>}
        <input type="text" placeholder="First Name" value={firstName} onChange={handleFirstNameChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <input type="text" placeholder="Last Name" value={lastName} onChange={handleLastNameChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <input type="text" placeholder="Username" value={username} onChange={handleUsernameChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <input type="email" placeholder="Email" value={email} onChange={handleEmailChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <input type="email" placeholder="Confirm Email" value={confirmEmail} onChange={handleConfirmEmailChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <input type="password" placeholder="Password" value={password} onChange={handlePasswordChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <input type="password" placeholder="Confirm Password" value={confirmPassword} onChange={handleConfirmPasswordChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <input type="text" placeholder="Phone Number" value={phoneNumber} onChange={handlePhoneNumberChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <select value={gender} onChange={handleGenderChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }}>
          <option value="">Select Gender</option>
          <option value="MALE">Male</option>
          <option value="female">Female</option>
          <option value="other">Other</option>
        </select>
        <input type="text" placeholder="Country" value={country} onChange={handleCountryChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <input type="text" placeholder="City" value={city} onChange={handleCityChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <input type="text" placeholder="Zip Code" value={zipCode} onChange={handleZipCodeChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <textarea placeholder="Address" value={address} onChange={handleAddressChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        <input type="file" onChange={handleAvatarChange} style={{ width: '100%', marginBottom: '10px', padding: '10px', borderRadius: '5px' }} />
        {avatarError && <div style={{ color: 'red', marginBottom: '10px' }}>{avatarError}</div>}
        <button type="submit" style={{ width: '100%', backgroundColor: '#4CAF50', color: 'white', padding: '10px', borderRadius: '5px', cursor: 'pointer' }}>Register</button>
      </form>
    </div>
  );
}

export default RegisterPage;