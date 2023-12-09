import { Box, Text } from "@chakra-ui/react";

const SectionTitle = ({ mainTitle, subtitle }) => {
  return (
    <Box height="40vh">
      <Box display="flex" justifyContent="center">
        <Box textAlign="center" maxW="700px">
          <Text
            fontSize="md"
            color="brand.900"
            fontWeight="semibold"
            paddingTop="100px"
            paddingBottom="20px"
            fontFamily="poppins.500"
          >
            {subtitle}
          </Text>
          <Text fontSize="4xl" fontFamily="poppins.500">
            {mainTitle}
          </Text>
        </Box>
      </Box>
    </Box>
  );
};

export default SectionTitle;
