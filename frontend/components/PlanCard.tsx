import { CheckIcon } from "@chakra-ui/icons";
import { Box, Text, Flex, Card, Button } from "@chakra-ui/react";

const PlanCard = ({ name, price, features, color, subtitle }) => {
  return (
    <Card
      padding="40px"
      height="440px"
      width="340px"
      bgColor={color}
      borderRadius="2xl"
    >
      <Text fontSize="xl" fontFamily="poppins.500">
        {name}
      </Text>
      <Flex alignItems="baseline">
        <Text fontSize="4xl" fontFamily="poppins.600">
          R${price}
        </Text>
        <Text fontSize="xl" fontFamily="poppins.600">
          /mês
        </Text>
      </Flex>
      {subtitle}
      <Box>
        {features.map((item: Object, index: number) => {
          return (
            <Flex key={index} alignItems="center" gap="10px" marginBottom="30px">
              <CheckIcon />
              <Text fontFamily="poppins.400">{item}</Text>
            </Flex>
          );
        })}
      </Box>
      <Button
        bgColor="black"
        color="white"
        fontFamily="poppins.500"
        fontSize="14px"
        rounded="full"
        height="50px"
      >
        Escolher
      </Button>
    </Card>
  );
};

export default PlanCard;
