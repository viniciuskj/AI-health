import { extendTheme } from "@chakra-ui/react";

const theme = extendTheme({
  colors: {
    brand: { 900: "#01C6B2", 800: "#31DCCC", 700: "#13CDBA", 600: "#077B70" },
    offwhite: "#EEF3F6",
    card: "#EAF1F0",
    gray: "#7A7A7A",
    ice: "#E4ECF0",
  },
  fonts: {
    body: "Poppins, sans-serif",
    poppins: {
      400: "var(--font-poppins-400)",
      500: "var(--font-poppins-500)",
      600: "var(--font-poppins-600)",
    },
    inter: { 400: "var(--font-inter-400)", 500: "var(--font-inter-500)" },
  },
});

export default theme;
