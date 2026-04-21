import React from "react";
import "./Cards.css";

function Clubs() {

  const clubs = [
    {
      name: "Coding Club",
      desc: "Learn programming and build projects.",
      img: "https://images.unsplash.com/photo-1518770660439-4636190af475"
    },
    {
      name: "Robotics Club",
      desc: "Build robots and automation systems.",
      img: "https://images.unsplash.com/photo-1581092335397-9583eb92d232"
    },
    {
      name: "Photography Club",
      desc: "Improve photography skills.",
      img: "https://images.unsplash.com/photo-1502920917128-1aa500764cbd"
    },
    {
      name: "Dance Club",
      desc: "Participate in cultural events.",
      img: "https://images.unsplash.com/photo-1504609813442-a8924e83f76e"
    }
  ];

  return (
    <div className="page">

      <h2>Our Clubs</h2>

      <div className="card-container">

        {clubs.map((club, index) => (
          <div className="card" key={index}>

            <img src={club.img} alt={club.name} />

            <h3>{club.name}</h3>
            <p>{club.desc}</p>

          </div>
        ))}

      </div>

    </div>
  );
}

export default Clubs;