import React from "react";

export default function SearchBarLocation() {
  
    return (
      <div class="input-group mb-3">
        <input
          type="text"
          class="form-control"
          placeholder="Enter the drug name"
          aria-label="Enter the drug name"
          aria-describedby="basic-addon2"
        />
        <div class="input-group-append">
          <button type="button" class="btn btn-outline-primary">
            Search
          </button>
        </div>
      </div>
    );
  
}
