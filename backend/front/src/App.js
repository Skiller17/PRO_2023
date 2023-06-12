/*import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;*/










/*import './App.css';
import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import Home from "./components/Home";
import Login from "./components/Login"

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <NavigationBar />
                <div className="container-fluid">
                    <Routes>
                        <Route path="home" element={<Home />}/>
                        <Route path="login" element={<Login />}/>
                    </Routes>
                </div>
            </BrowserRouter>
        </div>
    );
}
export default App;
*/

import './App.css';
import React, {useState} from "react";
import {BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import Home from "./components/Home";
import Login from "./components/Login";
import Utils from "./utils/Utils";
import {connect} from "react-redux";
import SideBar from "./components/SideBar";
import CountryListComponent from "./ComponentLists/CountryListComponent";
import CountryComponent from "./comp/CountryComponent";
import ArtistListComponent from "./ComponentLists/ArtistListComponent";
import ArtistComponent from "./comp/ArtistComponent";
import MuseumListComponent from "./ComponentLists/MuseumListComponent";
import MuseumComponent from "./comp/MuseumComponent";
import MyAccountComponent from "./comp/MyAccountComponent";
import UserListComponent from "./ComponentLists/UserListComponent";
import PaintingsListComponent from "./ComponentLists/PaintingsListComponent";
import PaintingComponent from "./comp/PaintingComponent";

const ProtectedRoute = ({children}) => {
    let user = Utils.getUser();
    return user ? children : <Navigate to={'/login'} />
};




const App = props => {

    const [exp,setExpanded] = useState(true);
    return (
        <div className="App">
            <BrowserRouter>
                <NavigationBar toggleSideBar={() =>
                    setExpanded(!exp)}/>
                <div className="wrapper">
                    <SideBar expanded={exp} />
                    <div className="container-fluid">
                        { props.error_message &&  <div className="alert alert-danger m-1">{props.error_message}</div>}
                        <Routes>
                            <Route path="login" element={<Login />}/>
                            <Route path="home" element={<ProtectedRoute><Home/></ProtectedRoute>}/>
                            <Route path="account" element={<ProtectedRoute><MyAccountComponent/></ProtectedRoute>}/>
                            <Route path="countries" element={<ProtectedRoute><CountryListComponent/></ProtectedRoute>}/>
                            <Route path="countries/:id" element={<ProtectedRoute><CountryComponent /></ProtectedRoute>}/>
                            <Route path="artists" element={<ProtectedRoute><ArtistListComponent/></ProtectedRoute>}/>
                            <Route path="artists/:id" element={<ProtectedRoute><ArtistComponent /></ProtectedRoute>}/>
                            <Route path="museums" element={<ProtectedRoute><MuseumListComponent/></ProtectedRoute>}/>
                            <Route path="museums/:id" element={<ProtectedRoute><MuseumComponent /></ProtectedRoute>}/>
                            <Route path="paintings" element={<ProtectedRoute><PaintingsListComponent/></ProtectedRoute>}/>
                            <Route path="paintings/:id" element={<ProtectedRoute><PaintingComponent /></ProtectedRoute>}/>
                            <Route path="users" element={<ProtectedRoute><UserListComponent/></ProtectedRoute>}/>

                        </Routes>
                    </div>
                </div>
            </BrowserRouter>
        </div>
    );
}



function mapStateToProps(state) {
    const { msg } = state.alert;
    return { error_message: msg };
}

export default connect(mapStateToProps)(App);