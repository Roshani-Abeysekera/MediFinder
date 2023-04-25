import React, { Component } from "react";
import Map from "./Map";

class MapContainer extends Component {
  render() {
    return (
      <div >    
          <Map
            google={this.props.google}
            center={{ lat: 6.9270786, lng: 79.861243 }}
            height="300px"
            zoom={15} 
            userLocation={this.props.userLocation}
            setLocation={this.props.setLocation}
          />
          
        </div>
    
    );
  }
}

export default MapContainer;