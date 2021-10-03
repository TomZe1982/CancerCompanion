import styled from "styled-components/macro";

export default function Pulsation({props}) {


    return (<Wrapper {...props}>
        <div></div>
    </Wrapper>)


}

const Wrapper = styled.div`
  display: inline-block;
  position: relative;
  width: 80px;
  height: 80px;
  div {
    position: relative;
    top: 170px;
    left: 20px;
    filter: blur(10px);
    width: 80px;
    height: 80px;
    border-radius: 50%;
    background: red;
    animation: lds-grid 4s linear infinite;
  }

  @keyframes lds-grid {
    0%,
    100% {
      opacity: 0.2;
    }
    50% {
      opacity: 0.05;
    }
  }
    `