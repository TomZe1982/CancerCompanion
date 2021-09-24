import styled from 'styled-components/macro'
import {css} from 'styled-components'

export default styled.button`
  display: inline-block;
  padding: var(--size-xxl);
  background: whitesmoke;
  border: 1px solid var(--accent);
  color: dimgray;
  font-size: 2em;
  border-radius: var(--size-xs);
  margin : 5px;

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
