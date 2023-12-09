import { Card, Text } from "@chakra-ui/react";

const DateCard = ({ month, day, current }) => {
  return (
    <Card
      width="60px"
      height="60px"
      bgColor={current ? "brand.900" : "brand.600"}
      borderRadius="2xl"
      justifyContent="center"
      alignItems="center"
    >
      <Text fontFamily="poppins.500" fontSize="12px">
        {month}
      </Text>
      <Text fontFamily="poppins.500" fontSize="24px">
        {day}
      </Text>
    </Card>
  );
};

export default DateCard;
