import { Box, Text, Flex } from "@chakra-ui/react";

const ProgressBar = ({ percentage, reason, height }) => {
  return (
    <Box textAlign="center">
      <Flex direction="column" alignItems="center" justifyContent="center">
        <Text fontSize="3xl" fontFamily="inter.500">
          {percentage}%
        </Text>
        <Box
          position="relative"
          height={height}
          width="100px"
          bgColor="brand.800"
        >
          <Box
            position="absolute"
            top={0}
            left={0}
            width="100%"
            height={`${100 - percentage}%`}
            bgColor="brand.700"
          />
        </Box>
        <Text paddingTop="10px" fontFamily="inter.400">
          {reason}
        </Text>
      </Flex>
    </Box>
  );
};

export default ProgressBar;
