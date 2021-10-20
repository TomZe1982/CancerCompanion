import styled from "styled-components/macro";
import {deleteTherapy} from "../../service/apiService";
import {useAuth} from "../../auth/AuthProvider";



export default function TimeLineItem({reloadPage, fetchedUserName, therapy}) {
    const {token} = useAuth()

    const handleDelete = () => {
        deleteTherapy(fetchedUserName, therapy.therapyId, token)
            .then(reloadPage)
    }

    return (
        <WrapperItem>
            <WrapperContent>
                <DeleteButton onClick={handleDelete}>X</DeleteButton>
                <Date>{therapy.date}</Date>
                <p>{therapy.title}</p>
                <Details>{therapy.description}</Details>
                <Circle></Circle>
            </WrapperContent>
        </WrapperItem>
    )
}

const DeleteButton = styled.button`
  background-color: darksalmon;
  color: dimgray;
    width: 20px;
    height: 20px;
`

const Date = styled.p`
  background-color: antiquewhite;
  font-size: 15px;
`

const Details = styled.details`
  color: dimgrey;

`

const WrapperItem = styled.div`
  display: flex;
  justify-content: flex-end;
  padding-right: 30px;
  margin: 10px 0;
  width: 50%;

  &:nth-child(odd) {
    align-self: flex-end;
    justify-content: flex-start;
    padding-right: 0;
    padding-left: 30px;
  }
`

const Circle = styled.span`
  background-color: whitesmoke;
  border: 4px solid dimgrey;
  border-radius: 50%;
  position: absolute;
  top: calc(50% - 10px);
  right: -40px;
  width: 20px;
  height: 20px;
  z-index: 100;

  ${WrapperItem}:nth-child(odd) & {
    right: auto;
    left: -40px;
  }
`


const WrapperContent = styled.div`
  background-color: whitesmoke;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  padding: 5px;
  position: relative;
  max-width: 100%;
  text-align: right;
  border-radius: 5px;
  width: 300px;

  &::after {
    content: '';
    background-color: whitesmoke;
    height: 15px;
    width: 15px;
    position: absolute;
    transform: rotate(45deg);
    right: -7.5px;
    top: calc(50% - 7.5px);
  }

  ${WrapperItem}:nth-child(odd) & {
    text-align: left;
    align-items: flex-start;

    &::after {
      content: '';
      background-color: whitesmoke;
      height: 15px;
      width: 15px;
      position: absolute;
      transform: rotate(45deg);
      left: -7.5px;
      top: calc(50% - 7.5px);
    }
  }
`


