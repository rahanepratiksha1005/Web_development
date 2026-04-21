import React from "react";
import "./Cards.css";

function Workshops() {

  const workshops = [
    {
      name: "Web Development",
      desc: "Learn HTML, CSS and React.",
      img: "https://images.unsplash.com/photo-1498050108023-c5249f4df085"
    },
    {
      name: "AI Workshop",
      desc: "Introduction to Artificial Intelligence.",
      img: "https://images.unsplash.com/photo-1555255707-c07966088b7b"
    },
    {
      name: "Cyber Security",
      desc: "Learn ethical hacking basics.",
      img: "https://images.unsplash.com/photo-1550751827-4bd374c3f58b"
    },
    {
      name: "UI/UX Design",
      desc: "Design modern user interfaces.",
      img: "https://images.unsplash.com/photo-1586717799252-bd134ad00e26"
    }
  ];

  return (
    <div className="page">

      <h2>Workshops</h2>

      <div className="card-container">

        {workshops.map((workshop, index) => (
          <div className="card" key={index}>

            <img src={workshop.img} alt={workshop.name} />

            <h3>{workshop.name}</h3>
            <p>{workshop.desc}</p>

          </div>
        ))}

      </div>

    </div>
  );
}

export default Workshops;