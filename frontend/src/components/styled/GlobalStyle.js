import {createGlobalStyle} from "styled-components";


export default createGlobalStyle`
    :root{
      --background: antiquewhite;
      --accent: darksalmon;
      --neutral-dark: #666;
      --neutral-light: #efefef;
      --error: var(--accent);

      --size-xs: 4px;
      --size-s: 8px;
      --size-m: 12px;
      --size-l: 16px;
      --size-xl: 24px;
      --size-xxl: 32px;
    }

    * {
      box-sizing: border-box;
    }

    html, body {
      margin: 0;
      font-family: Verdana;
    }
    
    p{
      text-align: start;
      color: dimgrey;
      margin: 10px 15px;
    }
    
    h1{
      text-align: center;
      color: var(--accent);
    }
    
    h2{
      text-align: center;
      color: var(--accent);
    }

    h3{
      text-align: center;
      color: dimgrey;
    }

    h4{
      text-align: center;
      color: dimgrey;
    }
    
`


