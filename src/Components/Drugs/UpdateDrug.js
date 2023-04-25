import React, { Component, useEffect } from 'react'
//import DrugService from '../services/DrugService';
import { Grid } from '@material-ui/core';
import Button from "@mui/material/Button";
import Stack from '@mui/material/Stack';
import axios from 'axios';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import HeaderComponent from '../HeaderComponent';


export default function UpdateDrug() {

    const { state } = useLocation();
    const { result } = state;

    let navigate = useNavigate();
    const tokenAccess = {

        headers: { Authorization: `Bearer ` }

    };

    const location = useLocation();
    const [id, setId] =  React.useState("");
    const [brandName, setBrandName] = React.useState("");
    const [geneticName, setGeneticName] = React.useState("");
    const [type, setType] = React.useState("");
    const [availability, setAvailability] = React.useState(true);


   React.useEffect ( async () => {
        console.log(result);
        setId(result.id)
        setBrandName(result.drugName)
        setGeneticName(result.geneticName)
        setType(result.type)
        setAvailability(result.availability)
    }, []
  );


    const handleUpdateDrug = async () => {
        try {
            const result = await axios.put(
                "http://localhost:8080/drugs/"+id,
                {
                    drugName: brandName,
                    geneticName: geneticName,
                    type: type,
                    availability: availability,
                }, tokenAccess
            );
            if (result.data) {
                console.log("Drug Updated successfully");
                navigate("/ViewAllDrugs")
                
            } else {
                console.log("Drug Update unsuccessful");
                navigate("/ViewAllDrugs")
            }
        } catch (error) {
            alert(error);
        }
    };


    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        //sample console log to test inputs
        console.log("Submit Drug");
    };

    const handleCancel = () => {
        navigate("/ViewAllDrugs");

    }

    const getTitle = () => {
        console.log(location.pathname);
        if (location.pathname === "/adddrug") {
            return <h1 className="text-center">Add your medicine...</h1>
        } else {
            return <h1 className="text-center">Update Drug Information</h1>
        }
    }

    return (
        <div>
             <HeaderComponent/>
            <br></br>
            <div className="container" style={{marginTop:'5%'}}>
                <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3"  style={{padding:20}}>
                <h1 className="text-center">Update Drug Information</h1>
                        
                        <br></br>
                        <Grid container spacing={2} >
                            <Grid container item xs={5} md={6} lg={6} direction="column" >
                               <div style={{textAlign:'left', marginLeft:20}} className="card-body">
                                    <form>
                                        <div className="form-group">
                                            <label> Brand Name: </label>
                                            <br></br>
                                            <br></br>
                                            
                                        </div>
                                        <div className="form-group">
                                            <label style={{marginTop:12}}> Genetic Name: </label>
                                            <br></br>
                                            <br></br>
                                            
                                        </div>
                                        <div className="form-group">
                                            <label style={{marginTop:12}}> Type: </label>
                                            <br></br>
                                            <br></br>
                                           
                                        </div>
                                        <div className="form-group">
                                            <label style={{marginTop:13}}> Availability: </label>
                                            <br></br>
                                            <br></br>
                                        </div>
                                    </form>
                                </div>

                            </Grid>
                            <Grid container item xs={5} md={5} lg={6} direction="column" >
                            <div className="card-body">

                                <form onSubmit={handleSubmit} >

                                    <div className="form-group">

                                        <input placeholder="Brand Name" name="drugName" required className="form-control"
                                            value={brandName} onChange={e => setBrandName(e.target.value)}
                                        />

                                        <br></br>
                                    </div>
                                    <div className="form-group">

                                        <input placeholder={result.geneticName} name="geneticName" className="form-control"
                                            value={geneticName} onChange={e => setGeneticName(e.target.value)} />
                                        <br></br>
                                    </div>
                                    <div className="form-group">

                                        <input placeholder="Type" name="type" className="form-control"
                                            value={type} onChange={e => setType(e.target.value)} />
                                        <br></br>
                                    </div>
                                    <div className="form-group">
                                    <div className="radio">
                                        <label>
                                            <input
                                            type="radio"
                                            value= {true}
                                            name='availability'
                                            
                                            onChange={e => setAvailability(e.target.value)}
                                            />
                                            Yes
                                        </label>
                                    </div>
                                    </div>
                                    <div className="form-group">
                                    <div className="radio">
                                        <label>
                                            <input
                                            type="radio"
                                            value= {false}
                                            name='availability'
                                            
                                            onChange={e => setAvailability(e.target.value)}
                                            />
                                            No
                                        </label>
                                        </div>
                                        </div>

                                    {/* <div className="form-group">

                                        <input placeholder="Availaility" name="availaility" className="radio-button"
                                            value={availability} onChange={e => setAvailability(e.target.value)} />
                                        <br></br>
                                    </div> */}



                                    {/* <button className="btn btn-success" onClick={this.saveOrUpdateDrug}>Add</button>
                                    <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{ marginLeft: "10px" }}>Cancel</button> */}


                                    <Stack spacing={20} direction="row">

                                        <Button className="btn btn-success" onClick={() => { handleUpdateDrug(); }} disabled={!brandName | !geneticName | !type | !availability} variant="contained">Update</Button>
                                        <Button className="btn btn-danger" onClick={handleCancel} style={{ marginLeft: "12px" }} variant="contained">Cancel</Button>
                                    </Stack>

                                </form>

                            </div>
                            </Grid>

                        </Grid>

                    </div>

                </div>
            </div >
        </div >
    );

}