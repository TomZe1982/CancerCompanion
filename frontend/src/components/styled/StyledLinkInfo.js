import styled from "styled-components/macro";
import {Link} from "react-router-dom";

export default styled(Link)`
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: 100%;
  text-decoration: none;
  text-align: center;
  color: var(--accent);
  width: 100%;
  height: 100%;
  padding: 20px 0px;
  border: 1px solid var(--accent);
  box-sizing: content-box;
  border-radius: 12px;
  box-shadow: 1px 2px 8px #666;
  margin: 20px 0px;
  align-content: center;
  align-items: center;
  justify-items: center;

`