import { Input } from "@chakra-ui/react";

const FormInput = ({ placeholder }) => {
  return (
    <Input
      height="60px"
      borderRadius="8px"
      placeholder={placeholder}
      fontFamily="poppins.500"
      sx={{ "&::placeholder": { opacity: 1 } }}
    />
  );
};

export default FormInput;
