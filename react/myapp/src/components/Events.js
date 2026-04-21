import React from "react";

function Events(){

const events = [
{
name:"Hackathon",
date:"10 April 2026",
img:"https://images.unsplash.com/photo-1504384308090-c894fdcc538d"
},
{
name:"Technical Quiz",
date:"15 April 2026",
img:"https://images.unsplash.com/photo-1518770660439-4636190af475"
},
{
name:"Coding Competition",
date:"20 April 2026",
img:"https://images.unsplash.com/photo-1517694712202-14dd9538aa97"
}
]

return(

<div className="container">

<h2>Upcoming Events</h2>

<div className="event-grid">

{events.map((event,index)=>(
<div className="event-card" key={index}>

<img src={event.img} alt="event"/>

<h3>{event.name}</h3>

<p>{event.date}</p>

<button>Register</button>

</div>
))}

</div>

</div>

)

}

export default Events;