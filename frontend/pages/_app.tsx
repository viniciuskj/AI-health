import { ChakraProvider } from "@chakra-ui/react";
import theme from "../styles/theme/theme";
import { Inter, Poppins } from "next/font/google";
import { UserProvider } from '../context/UserContext';

const inter400 = Inter({ subsets: ["latin"], weight: ["400"] });
const inter500 = Inter({ subsets: ["latin"], weight: ["500"] });

const poppins400 = Poppins({ subsets: ["latin"], weight: ["400"] });
const poppins500 = Poppins({ subsets: ["latin"], weight: ["500"] });
const poppins600 = Poppins({ subsets: ["latin"], weight: ["600"] });

export default function App({ Component, pageProps }) {
  return (
    <>
      <style jsx global>
        {`
          :root {
            --font-inter-400: ${inter400.style.fontFamily};
            --font-inter-500: ${inter500.style.fontFamily};
            --font-poppins-400: ${poppins400.style.fontFamily};
            --font-poppins-500: ${poppins500.style.fontFamily};
            --font-poppins-600: ${poppins600.style.fontFamily};
          }
        `}
      </style>
      <ChakraProvider theme={theme}>
      <UserProvider>
        <Component {...pageProps} />
        </UserProvider>
      </ChakraProvider>
    </>
  );
}
