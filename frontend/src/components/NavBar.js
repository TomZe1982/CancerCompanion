import {NavLink, useHistory} from "react-router-dom";
import styled from "styled-components/macro";
import * as PropTypes from "prop-types";
import StartScreen from "../pages/StartScreen";


function Button(props) {
    return null;
}

Button.propTypes = {
    onClick: PropTypes.any,
    children: PropTypes.node
};
export default function NavBar({ user, ...props }){

    const history = useHistory()
    return(
        <Wrapper {...props}>
            <NavLink exact to="/">Home</NavLink>
            <button onClick={history.goBack}>Back</button>
            {user && <NavLink to = "/logout">Logout</NavLink>}
            {!user && <NavLink to="/login">Login</NavLink>}
            {user && user.role === "admin" && <NavLink to = "/admin">Admin</NavLink>}
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