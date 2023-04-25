import React, { Component } from 'react'
import { Grid } from '@material-ui/core';
import Button from "@mui/material/Button";
import Stack from '@mui/material/Stack';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import Box from '@mui/material/Box';
import { FixedSizeList } from 'react-window';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import HeaderComponent from '../HeaderComponent';

export default function ViewAllDrugs() {

     let navigate = useNavigate();

    const tokenAccess = {

        headers: { Authorization: `Bearer ` }

    };

    const [drugs, setDrugs] = React.useState([]);
    const [topTen, setTopTen] = React.useState([]);

    React.useEffect ( async () => {
        
          const result = await axios.get(
            "http://localhost:8080/load",
             tokenAccess
          );
          if (result.data) {
            if (result.data.length === 0) {
              console.log("No drugs available");
              
            } else {
              console.log("view drugs");
              console.log(result.data);
              setDrugs(result.data);
            }
          }
      }, []
    );

    React.useEffect ( async () => {
        
      const res = await axios.get(
        "http://localhost:8080/searchcounts/topten"
      );
      if (res.data) {
        if (res.data.length === 0) {
          console("Top Ten searched not available");
          
        } else {
          console.log("Top Ten Searched");
          console.log(res.data);
          setTopTen(res.data);
        }
      }
  }, []
);


    
 const addDrug = () => {
    navigate("/adddrug");    
  }
  
  const viewDrug = async(id) => {
    try {
      const result = await axios.get(
        "http://localhost:8080/drugs/"+id,tokenAccess
      );
      if (result.data) {
        console.log("Drug Available");
        console.log(result.data);
        navigate("/viewDrug", { state: { result: result.data } });  
      } else {
        console.log("Drug Not available");
      }
    } catch (error) {
      alert(error);
    } 
  }

  const updateDrug = async(id) => {
    try {
      const result = await axios.get(
        "http://localhost:8080/drugs/"+id,tokenAccess
      );
      if (result.data) {
        console.log("Drug Available");
        console.log(result.data);
        navigate("/drugs", { state: { result: result.data } });  
      } else {
        console.log("Drug Not available");
      }
    } catch (error) {
      alert(error);
    } 
  }


  
      const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        //sample console log to test inputs
        console.log("Submit Drug");
      };
    
      const deleteDrug = (id) =>{

        axios.delete("http://localhost:8080/drugs/" + +id,tokenAccess).then( res => {
          setDrugs(drugs.filter(item => item.id !== id))
        });

    }

      // const deleteDrug = id => setDrugs(drugs.filter(drug => drug.id !== id));
    
     return (
            <div>

              <HeaderComponent/>
              
               
              <Card sx={{ minWidth: 400 }}>
                <CardContent>
                  <div style={{ width: '100%',  marginLeft: 30, marginRight: 30 , marginBottom:50}}>
      
                    <Grid container spacing={2} >
                      <Grid container xs={12} sm={9} md={9} direction="column" >
                      <h3 className="text-center" style={{ padding: 20}}>List of Available Drugs</h3>
                        <CardActions>
                          <Button style={{ marginLeft: 30, marginRight: 500 }} className="btn btn-primary" onClick={addDrug} variant="contained">Add new</Button>
                          
                        </CardActions>
                        <br></br>
                        <div className="row">
                          <table className="table table-striped table-bordered">
      
                            <thead>
                              <tr>
                                <th> Brand name</th>
                                <th> Genetic name</th>
                                <th> Type</th>
                                <th> Availability</th>
                                <th> Actions</th>
                              </tr>
                            </thead>
                            <tbody>
                              {
                                drugs.map(
                                  drug =>
                                    <tr key={drug.id}>
                                      <td> {drug.drugName} </td>
                                      <td> {drug.geneticName}</td>
                                      <td> {drug.type}</td>
                                      <td> {String(drug.availability)}</td>
                                      <td>
                                       <button onClick={() => updateDrug(drug.id)} className="btn btn-info">Update </button>
                                       <button style={{ marginLeft: "10px" }} onClick={() => deleteDrug(drug.id)} className="btn btn-danger">Delete </button>
                                       <button style={{ marginLeft: "10px" }} onClick={() => viewDrug(drug.id)} className="btn btn-info">View </button>
                                     </td>
                                    </tr>
                                )
                              }
                            </tbody>
                            
                          </table>
                        </div>
      
                      </Grid>
                      <Grid xs={12} sm={3} md={3} direction="column" sx={{mt:50}}  style={{ paddingTop: 80}}>
                      <h3>Top Ten Searched</h3>
                      
                         <Stack direction="column" spacing={3} sx={{mt:5}} >
                         {topTen.map( topTen => (          
                                      <h6>{topTen}</h6>         
                                    ))}                                        
                        </Stack>                     
                      
                      </Grid>
                    </Grid>
                  </div>
      
                </CardContent>
              </Card>
            </div>
          );
}