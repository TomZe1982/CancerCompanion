import TimeLineItem from "./TimeLineItem";
import styled from "styled-components/macro";


export default function TimeLine({reloadPage, fetchedUserName, therapyList}) {

    const therapyListMap = therapyList.map((therapy) => (
        <TimeLineItem reloadPage={reloadPage} fetchedUserName={fetchedUserName} therapy={therapy} key={therapy.id}/>
    ))

    return (
        <Wrapper>
            {therapyListMap}
        </Wrapper>
    )

}

const Wrapper = styled.div`
  background-color: antiquewhite;
  display: flex;
  flex-direction: column;
  margin: 20px 0;
  position: relative;
  width: 400px;
  
  
  &::after{
    background-color: dimgray;
    content: '';
    position: absolute;
    left: calc(50% - 1.5px);
    width: 3px;
    height: 100%;
  }
`