import React, { useState } from 'react';

function Feedback() {
  const [rating, setRating] = useState('');
  const [comment, setComment] = useState('');

  const handleSubmitFeedback = async (e) => {
    e.preventDefault();
    try {
      console.log('Feedback submitted:', { rating, comment });
    } catch (error) {
      console.error('Error submitting feedback:', error);
    }
  };

  return (
    <div>
      <h2>Feedback</h2>
      <form onSubmit={handleSubmitFeedback}>
        <input type="number" placeholder="Rating" value={rating} onChange={(e) => setRating(e.target.value)} />
        <textarea placeholder="Comment" value={comment} onChange={(e) => setComment(e.target.value)}></textarea>
        <button type="submit">Submit Feedback</button>
      </form>
    </div>
  );
}

export default Feedback;
