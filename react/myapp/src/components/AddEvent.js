import React,{useState} from "react";

function AddEvent(){

const [name,setName] = useState("")
const [date,setDate] = useState("")

const submit=(e)=>{
e.preventDefault()
alert("Event Added Successfully!")
setName("")
setDate("")
}

return(

<div className="container">

<h2>Add Event</h2>

<form className="form" onSubmit={submit}>

<input
type="text"
placeholder="Event Name"
value={name}
onChange={(e)=>setName(e.target.value)}
required
/>

<input
type="date"
value={date}
onChange={(e)=>setDate(e.target.value)}
required
/>

<button>Add Event</button>

</form>

</div>

)

}

export default AddEvent;