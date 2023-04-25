import "./App.css";
import ErrorPage from "./views/ErrorPage";
import LocationSearch from "./views/LocationSearch";
import ResultPage from "./views/ResultPage";
import DrugSearch from './views/DrugSearch';

import AddDrug from "./Components/Drugs/AddDrug";
import ViewAllDrugs from "./Components/Drugs/ViewAllDrugs";
import SimilarProducts from "./Components/SimilarProducts";

import Login from "./views/Login";
import CustomerRegisterForm from "./Components/Register/CustomerRegisterForm";
import AgentRegisterForm from "./Components/Register/AgentRegisterForm";
import MailCheck from "./Components/Register/MailCheck";

import { React, useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
  Navigate,
} from "react-router-dom";
import axios from "axios";
import ViewDrug from "./Components/Drugs/ViewDrug";
import UpdateDrug from "./Components/Drugs/UpdateDrug";

function App() {
  axios.interceptors.request.use((config) => {
    console.log(
      "access token: " + `Bearer ` + localStorage.getItem("access_token")
    );
    config.headers.Authorization =
      `Bearer  ` + localStorage.getItem("access_token");

    return config;
  });

  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path="/login" element={<Login />} />
          <Route path="/search" element={<SimilarProducts />} />

          <Route
            path="/customerRegister"
            element={<CustomerRegisterForm />}
          ></Route>
          <Route path="/agentRegister" element={<AgentRegisterForm />}></Route>
          <Route path="/mailCheck" element={<MailCheck />}></Route>

          <Route path="/locationsearch" element={<LocationSearch />} />
          <Route path="/drugsearch" element={<DrugSearch/>} />
          <Route path="/searchResult" element={<ResultPage />} />
          <Route path="/error" element={<ErrorPage />} />
          <Route path="/adddrug" element={<AddDrug />} />
          <Route path="/ViewAllDrugs" element={<ViewAllDrugs />} />
          <Route path="/ViewDrug" element={<ViewDrug />} />
          <Route path="/drugs" element={<UpdateDrug />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
