import React from "react";
import { useLocation } from "react-router-dom";
import { Table, TableRow, TableCell, Button } from "@mui/material";
import { useNavigate, useParams } from 'react-router-dom';
import HeaderComponent from "../HeaderComponent";

export default function ViewDrug() {

    const { state } = useLocation();
    const { result } = state;

    let navigate = useNavigate();

    console.log("result");
    console.log(result);

    const handleCancel = () => {
        navigate("/ViewAllDrugs");

    }

    const tokenAccess = {

        headers: { Authorization: `Bearer ` }

    };
    return (
        <div>
             <HeaderComponent/>
            <br></br>
            <div className="container" style={{marginTop:'5%'}}>
            <div className="card col-md-6 offset-md-3">


                <div>
                    <div className="card-body">
                        <h1 className="text-center"> View Drug Information</h1>
                        
                        <br></br>
                        <Table>
                            <TableRow>
                                <TableCell variant="head">Drug Name</TableCell>
                                <TableCell>{result.drugName}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell variant="head">Genetic Name</TableCell>
                                <TableCell>{result.geneticName}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell variant="head">Type</TableCell>
                                <TableCell>{result.type}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell variant="head">Availability</TableCell>
                                <TableCell>{String(result.availability)}</TableCell>
                            </TableRow>
                        </Table>
                        <br></br>
                        <Button className="btn btn-danger" onClick={handleCancel} style={{ marginLeft: "200px" }} variant="contained">Go Back</Button>
                    </div>

                </div>

              </div>
            </div>
        </div>
    );

}