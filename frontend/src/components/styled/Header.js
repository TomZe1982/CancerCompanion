import styled from 'styled-components/macro'


export default function Header({title, ...props}){

    return(
        <Wrapper {...props}>
            {/*<img className="header__image" src = "CancerCompanion.png" alt = "header_image"/>*/}
            <h1 className="header__title">{title}</h1>
        </Wrapper>
    )
}

const Wrapper = styled.header`
  width: 100%;
  text-align: center;
  background: var(--background);
  color: var(--accent);
  
`
