import styled from 'styled-components/macro'

export default styled.div`
  background: var(--background);
  background-image: url("CancerCompanion.png");
  background-position: center;
  background-repeat: no-repeat;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: grid;
  place-items: center;
  grid-template-rows: min-content 1fr min-content;
`
