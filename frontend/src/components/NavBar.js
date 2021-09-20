import {NavLink, useHistory} from "react-router-dom";
import styled from "styled-components/macro";



export default function NavBar({ user}){
    const history = useHistory()


    return(
        <Wrapper>
            <NavBarButton onClick={history.goBack} >Back</NavBarButton>
            <NavLink exact to = "/" >Home</NavLink>
            {user && <NavLink to = "/logout" >Logout</NavLink>}
            {user && <NavLink to = "/profile" >Profil</NavLink>}
            {!user && <NavLink to = "/login" >Login</NavLink>}
            {user && <NavLink to = "/blogs" >Blogs</NavLink>}
            {user && user.role === "admin" && <NavLink to = "/admin" >Admin</NavLink>}
        </Wrapper>
    )

}

const Wrapper = styled.nav`
  border-top: 1px solid var(--neutral-dark);
  width: 100%;
  padding: var(--size-m);
  display: flex;
  overflow-y: scroll;

  a {
    flex-grow: 1;
    margin: 0 var(--size-l);
    text-align: center;
    text-decoration: none;
    color: var(--neutral-dark);
  }

  a.active {
    color: var(--accent);
  }
`
const NavBarButton = styled.button`
  flex-grow: 1;
  box-sizing: border-box;
  margin: 0 var(--size-l);
  background-color: var(--background);
  border: none;
  color: var(--neutral-dark);
  padding: 0;
  text-align: center;
  font-family: Verdana,serif;
  text-decoration: none;
  display: flex;
  font-size: 16px;
`
