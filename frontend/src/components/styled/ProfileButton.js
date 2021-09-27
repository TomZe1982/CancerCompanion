import styled from 'styled-components/macro'
import { css } from 'styled-components'

export default styled.button`
  display: inline-block;
  width: 200px;
  padding: var(--size-xs);
  background: var(--accent);
  border: 1px solid var(--accent);
  color: var(--neutral-light);
  font-size: 1em;
  border-radius: var(--size-xl);
  text-align:center;

  :disabled {
    border-color: var(--neutral-dark);
    background: var(--neutral-dark);
    color: var(--neutral-light);
  }

  ${props =>
    props.secondary &&
    css`
      background: none;
      color: var(--accent);
      border: 1px solid var(--accent);

      :disabled {
        border-color: var(--neutral-dark);
        background: none;
        color: var(--neutral-dark);
      }
    `}
`
