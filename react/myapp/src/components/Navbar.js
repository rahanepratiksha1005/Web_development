import React from "react";

function Navbar({ setPage }) {

  return (

    <div className="navbar">

      <h2>🎓 College Events</h2>

      <div className="nav-links">

        <button onClick={() => setPage("home")}>Home</button>

        <button onClick={() => setPage("events")}>Events</button>

        <button onClick={() => setPage("clubs")}>Clubs</button>

        <button onClick={() => setPage("workshops")}>Workshops</button>

        <button onClick={() => setPage("register")}>Register</button>

        <button onClick={() => setPage("add")}>Add Event</button>

      </div>

    </div>

  );

}

export default Navbar;