import React, { useState } from "react";
import "./Register.css";

function Register() {

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [event, setEvent] = useState("");
  const [payment, setPayment] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    alert("🎉 Registration Successful!");
    setName("");
    setEmail("");
    setEvent("");
    setPayment("");
  };

  return (
    <div className="register-container">

      <div className="register-card">

        <h2>🎟 Event Registration</h2>

        <form onSubmit={handleSubmit}>

          <input
            type="text"
            placeholder="👤 Enter Your Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />

          <input
            type="email"
            placeholder="📧 Enter Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <select
            value={event}
            onChange={(e) => setEvent(e.target.value)}
            required
          >
            <option value="">Select Event</option>
            <option>Hackathon - ₹200</option>
            <option>Technical Quiz - ₹100</option>
            <option>Coding Competition - ₹150</option>
          </select>

          <select
            value={payment}
            onChange={(e) => setPayment(e.target.value)}
            required
          >
            <option value="">Payment Method</option>
            <option>UPI</option>
            <option>Credit Card</option>
            <option>Debit Card</option>
            <option>Net Banking</option>
          </select>

          <button type="submit">Register Now 🚀</button>

        </form>

      </div>

    </div>
  );
}

export default Register;