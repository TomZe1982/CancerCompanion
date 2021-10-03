import styled from 'styled-components/macro'
import ImageUrl from './CancerCompanion.png'

export default styled.div`
  background: var(--background);
  background-image: url(${ImageUrl});
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
