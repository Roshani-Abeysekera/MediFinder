import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import { Link } from "react-router-dom";


export default function HeaderComponent() {

  const handleLogout = () => {
      localStorage.removeItem('access_token');
      console.log(localStorage.getItem('access_token'));

  }

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Medifinder
          </Typography>
          <Link to = "/">
          <Button variant="outlined" color="error" onClick={handleLogout}>Logout</Button>
          </Link>
        </Toolbar>
      </AppBar>
    </Box>
  );
}

