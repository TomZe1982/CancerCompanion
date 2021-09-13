import {NavLink} from "react-router-dom";
import styled from "styled-components/macro";

export default function NavBar(){

    return(
        <Wrapper>
            <NavLink to="/">Home</NavLink>
            <NavLink to="/login">Login</NavLink>
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