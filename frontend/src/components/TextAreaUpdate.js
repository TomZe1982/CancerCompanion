import styled from 'styled-components/macro'
import Label from './Label'

export default function TextAreaUpdate({
                                     type = 'text',
                                     value,
                                     onChange,
                                     name,
                                     title,
                                     ...props
                                 }) {
    return (
        <Label {...props}>
            {title}
            <Input type={type} value={value} onChange={onChange} name={name} />
        </Label>
    )
}

const Input = styled.textarea`
  width: 100%;
  height: 300px;
  
  font-size: 1em;
  padding: var(--size-s);
  margin-top: var(--size-s);
  border-radius: var(--size-s);
  line-break: auto;
`