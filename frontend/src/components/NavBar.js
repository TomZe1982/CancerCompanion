import {NavLink, useHistory} from "react-router-dom";
import styled from "styled-components/macro";



export default function NavBar({ user}){


    const history = useHistory()
    return(
        <Wrapper>
            <NavLink exact to = "/" >Home</NavLink>
            {<button onClick={history.goBack} >Back</button>}
            {user && <NavLink to = "/logout" >Logout</NavLink>}
            {user && <NavLink to = "/profile" >Profil</NavLink>}
            {!user && <NavLink to = "/login" >Login</NavLink>}
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
