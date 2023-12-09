import { Box, Text } from "@chakra-ui/react";

const ReportCard = ({ problem, chanceOfOccurrence, description }) => {
  return (
    <Box>
      <Text fontFamily="inter.500" marginTop="40px">
        {problem} | Chance de ocorrer: {chanceOfOccurrence}
      </Text>
      <Text fontFamily="inter.400" marginTop="15px" color="gray">
        {description}
      </Text>
    </Box>
  );
};

export default ReportCard;
