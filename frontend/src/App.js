import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'
import './App.css';
import StartScreen from "./pages/StartScreen";
import Login from "./pages/Login";
import Registration from "./pages/Registration";

import Profile from "./pages/Profile";
import AuthProvider from "./auth/AuthProvider";
import Logout from "./pages/Logout";
import Delete from "./pages/Delete";
import EditProfile from "./pages/EditProfile";
import MakeUpTutorials from "./pages/MakeUpTutorials";



export default function App() {


    return (
        <AuthProvider>
            <Router>
                <Switch>
                    <Route exact path="/" component={StartScreen}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/logout" component={Logout}/>
                    <Route path="/register" component={Registration}/>
                    <Route path="/edit" component ={EditProfile}/>
                    <Route path="/profile" component={Profile}/>
                    <Route path="/delete" component={Delete}/>
                    <Route path="/tutorials" component={MakeUpTutorials}/>
                </Switch>
            </Router>
        </AuthProvider>

    )
}


