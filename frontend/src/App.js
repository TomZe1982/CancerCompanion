import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'
import './App.css';
import StartScreen from "./pages/StartScreen";
import Login from "./pages/private/Login";
import Registration from "./pages/public/Registration";
import Profile from "./pages/private/Profile";
import AuthProvider from "./auth/AuthProvider";
import Logout from "./pages/private/Logout";
import Delete from "./pages/private/Delete";
import EditSettings from "./pages/private/EditSettings";
import MakeUpTutorials from "./pages/private/MakeUpTutorials";
import ProtectedRoute from "./auth/ProtectedRoute";
import Admin from "./pages/private/Admin";
import EditPassword from "./pages/private/EditPassword";
import AdminError from "./components/AdminError";
import Blogs from "./pages/private/blog/Blogs";
import BlogDetails from "./pages/private/blog/BlogDetails";
import InfoPage from "./pages/public/InfoPage";
import EachInformationPage from "./pages/public/EachInformationPage";
import AddInformation from "./pages/public/AddInformation";
import NewBlog from "./pages/private/blog/NewBlog";
import UpdateInformation from "./pages/public/UpateInformation";
import UpdateEachInformation from "./pages/public/UpdateEachInformation";
import TherapyPass from "./pages/private/TherapyPass";



export default function App() {


    return (
        <AuthProvider>
            <Router>
                <Switch>
                    <Route exact path="/" component={StartScreen}/>
                    <Route path="/info" component={InfoPage}/>
                    <Route path="/infodetails/:infoId" component={EachInformationPage}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/register" component={Registration}/>
                    <ProtectedRoute path="/logout" component={Logout}/>
                    <ProtectedRoute path="/editsettings" component ={EditSettings}/>
                    <ProtectedRoute path="/editpassword" component ={EditPassword}/>
                    <ProtectedRoute path="/profile" component={Profile}/>
                    <ProtectedRoute path="/delete" component={Delete}/>
                    <ProtectedRoute path="/tutorials" component={MakeUpTutorials}/>
                    <ProtectedRoute path="/blogs" component={Blogs}/>
                    <ProtectedRoute path="/userblogs/:fetchedUserNameForBlog" component={BlogDetails}/>
                    <ProtectedRoute path="/newblog" component={NewBlog}/>
                    <ProtectedRoute path="/therapy/:fetchedUserName" component={TherapyPass}/>
                    <ProtectedRoute adminOnly path="/admin" component={Admin}/>
                    <ProtectedRoute adminOnly path="/adminerror" component={AdminError}/>
                    <ProtectedRoute adminOnly path="/addinfos" component={AddInformation}/>
                    <ProtectedRoute adminOnly path="/updateinfos" component={UpdateInformation}/>
                    <ProtectedRoute adminOnly path="/updateeachinfo/:informationId" component={UpdateEachInformation}/>
                </Switch>
            </Router>
        </AuthProvider>

    )
}


