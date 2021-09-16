import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'
import './App.css';
import StartScreen from "./pages/StartScreen";
import Login from "./pages/Login";
import Registration from "./pages/Registration";
import Profile from "./pages/Profile";
import AuthProvider from "./auth/AuthProvider";
import Logout from "./pages/Logout";
import Delete from "./pages/Delete";
import EditSettings from "./pages/EditSettings";
import MakeUpTutorials from "./pages/MakeUpTutorials";
import ProtectedRoute from "./auth/ProtectedRoute";
import Admin from "./pages/Admin";
import EditPassword from "./pages/EditPassword";



export default function App() {


    return (
        <AuthProvider>
            <Router>
                <Switch>
                    <Route exact path="/" component={StartScreen}/>
                    <Route path="/login" component={Login}/>
                    <ProtectedRoute path="/logout" component={Logout}/>
                    <ProtectedRoute path="/register" component={Registration}/>
                    <ProtectedRoute path="/editsettings" component ={EditSettings}/>
                    <ProtectedRoute path="/editpassword" component ={EditPassword}/>
                    <ProtectedRoute path="/profile" component={Profile}/>
                    <ProtectedRoute path="/delete" component={Delete}/>
                    <ProtectedRoute path="/tutorials" component={MakeUpTutorials}/>
                    <ProtectedRoute adminOnly path="/admin" component={Admin}/>
                </Switch>
            </Router>
        </AuthProvider>

    )
}


