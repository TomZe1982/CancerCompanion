import styled from "styled-components/macro";
import {Link} from "react-router-dom";

export default styled(Link)`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: 100%;
  text-decoration: none;
  width: 100%;
  height: 100%;
  padding: 20px;
  border: 1px solid var(--accent);
  box-sizing: content-box;
  border-radius: 12px;
  box-shadow: 1px 2px 8px #666;
  margin: 20px;
  align-content: center;
  align-items: center;
  
  .user__image{
    grid-column: 1;
    grid-row: 1;
  }
  
  .user__name{
    grid-column: 2;
    grid-row: 1;
  }
`