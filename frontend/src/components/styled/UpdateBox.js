import styled from "styled-components/macro";

export default styled.div`
  background: antiquewhite;
  display: grid;
  grid-template-rows: 1fr 1fr;
  grid-template-columns: 1fr;
  width: 100%;
  height: 100%;
  padding: 2px 0px;
  border: 1px solid var(--accent);
  box-sizing: border-box;
  border-radius: 12px;
  box-shadow: 1px 2px 8px #666;
  margin: 5px 0px;
  text-align: center;
  justify-content: center;
  align-content: center;
  justify-items: center;
  align-items: center;
  
  .details{
    grid-row: 1;
    text-align: center;
    justify-items: center;
    align-items: center;
  }
  
  .button{
    grid-row: 2;
    display: flex;
    justify-content: flex-start;
  }
  
  .link{
    grid-row: 2;
    display: flex;
    justify-content: flex-end;
  }
`