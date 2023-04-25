import React, { useState, useEffect } from "react";
import { Grid, Paper, TextField, Button } from "@material-ui/core";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import { FormHelperText as helperText } from "@material-ui/core";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import Avatar from "@mui/material/Avatar";

import MapContainer from "../Map/MapContainer";
const theme = createTheme();

const AgentRegisterForm = (props) => {
  const paperStyle = {
    padding: "10px 10px",
    width: 1200,
    height: 600,
    margin: "70px auto",
  };
  const headerStyle = { margin: 0 };
  const marginTop = { marginTop: 15 , marginLeft: 20 };

  const [firstName, setFirstname] = React.useState("");
  const [contactNumber, setContactnumber] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [ConfirmPassword, setConfirmPassword] = React.useState("");
  const [mapPosition, setLat] = useState({});
  const [isError, setIsError] = React.useState("");

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.watchPosition(function (position) {
        console.log("Latitude is :", position.coords.latitude);
        console.log("Longitude is :", position.coords.longitude);
        setLat({
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        });
      });
    }
  }, []);

  console.log("this " + mapPosition.lat);

  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    //sample console log to test inputs
    console.log({
      firstName: data.get("first_name"),
      lastName: data.get("last_name"),
    });
  };

  const checkValidation = (e) => {
    const ConfirmPassword = e.target.value;
    setConfirmPassword(ConfirmPassword);
    if (password != ConfirmPassword) {
      setIsError("Confirm password should be matched with password");
    } else {
      setIsError("");
    }
  };

  const handleCancel = () =>{
     navigate("/login");

  }

  const handleReg = async () => {
    try {

      const unintercetedAxiosInstance = axios.create();
      const result = await unintercetedAxiosInstance.post(
        "http://localhost:8080/agent/registration",
        {
          firstName: firstName,
          email: email,
          contactNumber: contactNumber,

          latitude: mapPosition.lat,
          longitude: mapPosition.lng,

          password: password,
          roles: [
            {
              roleId: 2,
              role: "AGENT",
            },
          ],
        }
      );
      if (result.data === "Successful") {
        console.log("Agent register sucessful");
        navigate("/mailCheck");
      } else {
        alert("Unsuccessful Registration");
        console.log("Unsuccessful Registration");
      }
    } catch (error) {
      alert(error);
    }
  };

  return (
    <Grid>
      <Paper variant="outlined" sx={{ p: 2 }} elevation={20} style={paperStyle}>
        <ThemeProvider theme={theme}>
          <CssBaseline />
          <Grid align="center">
            <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}></Avatar>
            <h2 style={headerStyle}>Agency Registration</h2>
          </Grid>
          <Formik
          // initialValues={initialValues}
          // validationSchema={validationSchema}
          >
            {(props) => (
              <Form onSubmit={handleSubmit}>
                <Grid container spacing={2}>
                  <Grid container item xs={6} direction="column">
                    <Field
                      as={TextField}
                      fullWidth
                      margin="normal"
                      required
                      autoComplete="off"
                      autoFocus
                      name="name"
                      type="text"
                      label="Name"
                      placeholder="Enter agency name"
                      //style={marginTop}
                      helperText={
                        <ErrorMessage name="name">
                          {(msg) => <div style={{ color: "red" }}>{msg}</div>}
                        </ErrorMessage>
                      }
                      value={firstName}
                      onChange={(e) => setFirstname(e.target.value)}
                    />
                    <Field
                      as={TextField}
                      name="email"
                      type="email"
                      fullWidth
                      margin="normal"
                      required
                      autoComplete="off"
                      autoFocus
                      label="Email"
                      placeholder="Enter agency email"
                      //style={marginTop}
                      helperText={
                        <ErrorMessage name="email">
                          {(msg) => <div style={{ color: "red" }}>{msg}</div>}
                        </ErrorMessage>
                      }
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                    />
                    <Field
                      as={TextField}
                      name="contactNumber"
                      fullWidth
                      margin="normal"
                      required
                      autoComplete="off"
                      autoFocus
                      label="Contact Number"
                      placeholder="Enter agency contact number"
                      //style={marginTop}
                      helperText={
                        <ErrorMessage name="contactNumber">
                          {(msg) => <div style={{ color: "red" }}>{msg}</div>}
                        </ErrorMessage>
                      }
                      value={contactNumber}
                      onChange={(e) => setContactnumber(e.target.value)}
                    />
                    <Field
                      as={TextField}
                      name="password"
                      type="password"
                      fullWidth
                      margin="normal"
                      required
                      autoComplete="off"
                      autoFocus
                      label="Password"
                      placeholder="Enter your password"
                      //style={marginTop}
                      helperText={
                        <ErrorMessage name="password">
                          {(msg) => <div style={{ color: "red" }}>{msg}</div>}
                        </ErrorMessage>
                      }
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                    <Field
                      as={TextField}
                      name="confirmPassword"
                      type="password"
                      fullWidth
                      margin="normal"
                      required
                      autoComplete="off"
                      autoFocus
                      label="Confirm Password"
                      placeholder="Confirm your password"
                      //style={marginTop}
                     
                      
                      value={ConfirmPassword}
                      onChange={(e) => checkValidation(e)}
                    />
                     <div style={{ color: "red" }}>{isError}</div>
                  </Grid>

                  <Grid container item xs={6} direction="column">
                    <div style={{ margin: "30px" }}>
                      {mapPosition.lat ? (
                        <div>
                          <MapContainer
                            userLocation={mapPosition}
                            setLocation={setLat}
                          />
                        </div>
                      ) : (
                        <>enable position</>
                      )}
                    </div>
                  </Grid>
                </Grid>
                <Button
                  type="submit"
                  variant="contained"
                  color="primary"
                  style={marginTop}
                  disabled={
                    !firstName |
                    !contactNumber |
                    !email |
                    !password |
                    !ConfirmPassword |
                    !mapPosition |
                    isError
                  }
                  onClick={() => {
                    handleReg();
                  }}
                >
                  Register
                </Button>
                <Button
                  type="submit"
                  variant="contained"
                  color="primary"
                  style={marginTop}
                  
                  onClick={() => {
                    handleCancel();
                  }}
                >Cancel</Button>
              </Form>
              
            )}
          </Formik>
         
        </ThemeProvider>
      </Paper>
    </Grid>
  );
};

export default AgentRegisterForm;
