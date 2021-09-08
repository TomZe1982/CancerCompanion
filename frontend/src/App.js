import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'
import './App.css';
import StartScreen from "./pages/StartScreen";
import Login from "./pages/Login";
import Registration from "./pages/Registration";
import {useEffect, useState} from "react";
import {getAllUser} from "./service/apiService";
import Profile from "./pages/Profile";


export default function App() {

    const[user, setUser] = useState({})

    useEffect(()=>{
        getAllUser()
            .then(user => setUser(user))
            .catch(error => console.error(error))
    })


    return (
        <Router>
            <Switch>
                <Route exact path="/" component={StartScreen}/>
                <Route path="/login" component={Login}/>
                <Route path="/register" component={Registration} user={user}/>
                <Route path="/profile" component={Profile} user={user}/>
            </Switch>
        </Router>

    )
}


