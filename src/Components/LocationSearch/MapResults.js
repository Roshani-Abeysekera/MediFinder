import React, { useState } from "react";
import { GoogleMap, InfoWindow, Marker } from "@react-google-maps/api";

const google = window.google;

const Map = (props) => {
  const [activeMarker, setActiveMarker] = useState(null);

  const handleActiveMarker = (marker) => {
    if (marker === activeMarker) {
      return;
    }
    setActiveMarker(marker);
  };
  let newId = 0;
  let agentLat = props.result[0].latitude;
  let agentLon = props.result[0].longitude;
  let lat = Number(props.userLtd);
  let lon = Number(props.userLng);

  const markers = [
    {
      id: 1,
      name: "User Location",
      position: { lat: lat, lng: lon },
    },

    {
      id: 2,
      name: "Agent location",
      position: { lat: agentLat, lng: agentLon },
    },
    ,
  ];

  const handleOnLoad = (map) => {
    const bounds = new window.google.maps.LatLngBounds();
    markers.forEach(({ position }) => bounds.extend(position));
    map.fitBounds(bounds);
  };
  console.log("awa");
  console.log(props.result);
  return (
    <GoogleMap
      onLoad={handleOnLoad}
      onClick={() => setActiveMarker(1)}
      defaultZoom={8}
      defaultCenter={{
        lat: 7.0430716,
        lng: 79.9606842,
      }}
      mapContainerStyle={{ width: "100vw", height: "100vh" }}
    >
      {props.result.map((item, id) => (
        <Marker
          key={id}
          icon="https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png"
          position={{ lat: item.latitude, lng: item.longitude }}
          onClick={() => handleActiveMarker(id)}
        >
          {activeMarker === id ? (
            <InfoWindow onCloseClick={() => setActiveMarker(null)}>
              <div>{item.firstName} Pharmacy</div>
            </InfoWindow>
          ) : null}
          {(newId = id + 1)}
          {(agentLat = item.latitude)}
          {(agentLon = item.longitude)}
        </Marker>
      ))}
      <Marker
        key={newId}
        icon="https://maps.gstatic.com/mapfiles/ms2/micons/rangerstation.png"
        position={{ lat: lat, lng: lon }}
        onClick={() => handleActiveMarker(newId)}
      >
        {activeMarker === newId ? (
          <InfoWindow onCloseClick={() => setActiveMarker(null)}>
            <div>My Location</div>
          </InfoWindow>
        ) : null}
      </Marker>
    </GoogleMap>
  );
};

export default Map;
