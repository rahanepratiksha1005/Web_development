import React, { useState } from "react";
import Navbar from "./components/Navbar";
import Home from "./components/Home";
import Events from "./components/Events";
import AddEvent from "./components/AddEvent";
import Register from "./components/Register";
import Clubs from "./components/Clubs";
import Workshops from "./components/Workshops";
import "./App.css";

function App() {

  const [page, setPage] = useState("home");

  const renderPage = () => {
    if (page === "home") return <Home />;
    if (page === "events") return <Events setPage={setPage} />;
    if (page === "register") return <Register />;
    if (page === "add") return <AddEvent />;
    if (page === "clubs") return <Clubs />;
    if (page === "workshops") return <Workshops />;
  };

  return (
    <div>
      <Navbar setPage={setPage} />
      {renderPage()}
    </div>
  );
}

export default App;