import { Box, Text, Flex, Center } from "@chakra-ui/react";

const Step = ({ number, label }) => {
  return (
    <Box display="flex" alignItems="center" columnGap="20px" width="300px">
      <Box
        minWidth="64px"
        minHeight="64px"
        rounded="full"
        bgColor="black"
        display="flex"
        alignItems="center"
        justifyContent="center"
      >
        <Center>
          <Text fontSize="4xl" color="brand.900" fontFamily="inter.500">
            {number}
          </Text>
        </Center>
      </Box>
      <Text fontSize="xl" fontFamily="poppins.400">
        {label}
      </Text>
    </Box>
  );
};

export default Step;
