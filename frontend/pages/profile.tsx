import {
  Box,
  Button,
  Checkbox,
  Flex,
  Input,
  Radio,
  RadioGroup,
  Select,
  SimpleGrid,
  Stack,
  Text,
} from "@chakra-ui/react";
import { useRouter } from "next/router";
import { useState } from "react";
import { IoIosArrowBack } from "react-icons/io";

interface Data {
  userName: string;
  dateOfBirth: string;
  weight: number;
  height: number;
  gender: string;
  exerciseTime: number;
}

const Profile = () => {
  const [history, setHistory] = useState("false");
  const [error, setError] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isButtonEnabled, setIsButtonEnabled] = useState(false);
  const [buttonClicked, setButtonClicked] = useState(false);
  const router = useRouter();
  const [data, setData] = useState<Data>({
    userName: "",
    dateOfBirth: "",
    weight: null,
    height: null,
    gender: "",
    exerciseTime: null,
  });

  const convertDateToServerFormat = (dateString: any) => {
    const [year, month, day] = dateString.split("T")[0].split("-").map(Number);
    return {
      dia: day,
      mes: month,
      ano: year,
    };
  };

  const fetchData = async () => {
    setError("");
    setErrorMessage("");

    const dateOfBirthData = convertDateToServerFormat(data.dateOfBirth);

    try {
      const response = await fetch(
        "http://localhost:8080/profile/registration",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            name: data.userName,
            weight: data.weight,
            height: data.height,
            exerciseTime: data.exerciseTime,
            dateOfBirth: dateOfBirthData,
          }),
        }
      );

      if (!response.ok) {
        const errorData = await response.text();
        console.log(errorData);
        setError(errorData);
        throw new Error(errorData);
      }

      console.log("Dados do perfil enviados para o servidor com sucesso!");
    } catch (error) {
      console.error("Erro ao enviar dados: ", error.message);
    }
  };

  const handleButtonClick = () => {
    if (
      data?.userName &&
      data?.gender &&
      data?.weight &&
      data?.height &&
      data?.exerciseTime
    ) {
      fetchData();
    } else {
      setErrorMessage("Por favor, preencha todos os campos.");
    }
    setButtonClicked(true);
  };

  return (
    <Box backgroundColor="offwhite" h="100vh">
      <Box paddingTop="100px" marginX="310px" justifyContent="center">
        <Flex
          justifyContent="space-between"
          alignItems="center"
          marginBottom="70px"
        >
          <Text
            fontFamily="poppins.400"
            fontSize="3xl"
            sx={{ textAlign: "center" }}
          >
            Meus dados
          </Text>

          <Flex
            cursor="pointer"
            alignItems="center"
            gap="10px"
            onClick={() => {
              router.push("/dashboard");
            }}
          >
            <IoIosArrowBack />
            <Text fontFamily="poppins.400" sx={{ textAlign: "center" }}>
              Voltar
            </Text>
          </Flex>
        </Flex>
        <SimpleGrid columns={2} spacing={10}>
          <Input
            h="50px"
            fontFamily="poppins.500"
            placeholder="Nome"
            value={data.userName}
            onChange={(event: any) =>
              setData({ ...data, userName: event.target.value })
            }
          />
          <Input
            h="50px"
            fontFamily="poppins.500"
            placeholder="Data de nascimento"
            type="date"
            value={data.dateOfBirth}
            onChange={(event: any) =>
              setData({ ...data, dateOfBirth: event.target.value })
            }
          />
          <Input
            h="50px"
            fontFamily="poppins.500"
            placeholder="Peso (kg)"
            value={data.weight}
            onChange={(event: any) =>
              setData({ ...data, weight: event.target.value })
            }
          />
          <Input
            h="50px"
            fontFamily="poppins.500"
            placeholder="Altura (cm)"
            value={data.height}
            onChange={(event: any) =>
              setData({ ...data, height: event.target.value })
            }
          />
          <Select
            h="50px"
            fontFamily="poppins.500"
            placeholder="Gênero"
            value={data.gender}
            onChange={(event: any) =>
              setData({ ...data, gender: event.target.value })
            }
          >
            <option value="M">Masculino</option>
            <option value="F">Feminino</option>
          </Select>
          <Input
            h="50px"
            fontFamily="poppins.500"
            placeholder="Tempo médio de exercícios (em minutos)"
            value={data.exerciseTime}
            onChange={(event: any) =>
              setData({ ...data, exerciseTime: event.target.value })
            }
          />
        </SimpleGrid>

        <Text fontFamily="poppins.500" marginTop="40px">
          Doencas na familia?
        </Text>
        <RadioGroup onChange={setHistory} value={history} marginY="30px">
          <Stack>
            <Radio colorScheme="blackAlpha" size="lg" value={"false"}>
              Nao
            </Radio>
            <Radio colorScheme="blackAlpha" size="lg" value={"true"}>
              Sim
            </Radio>
          </Stack>
        </RadioGroup>
        {history === "true" && (
          <Input h="50px" placeholder="Quais?" value="" onChange={() => {}} />
        )}

        <Box display="flex" justifyContent="space-between" marginTop="30px">
          <Box ml="auto">
            <Button
              color="brand.900"
              fontFamily="inter.500"
              backgroundColor="black"
              width="150px"
              onClick={handleButtonClick}
            >
              Salvar
            </Button>
          </Box>
        </Box>
        {buttonClicked && errorMessage && (
          <Box py={2}>
            <Text color="red" fontSize="xs">
              {errorMessage}
            </Text>
          </Box>
        )}
        <Box py={2}>
          <Text color="red" fontSize="xs">
            {error}
          </Text>
        </Box>
      </Box>
    </Box>
  );
};

export default Profile;
