import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'
import './App.css';
import StartScreen from "./pages/StartScreen";
import Login from "./pages/Login";
import Registration from "./pages/Registration";

import Profile from "./pages/Profile";
import AuthProvider from "./auth/AuthProvider";



export default function App() {


    return (
        <AuthProvider>
            <Router>
                <Switch>
                    <Route exact path="/" component={StartScreen}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/register" component={Registration}/>
                    <Route path="/profile" component={Profile}/>
                </Switch>
            </Router>
        </AuthProvider>

    )
}


