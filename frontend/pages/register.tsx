import {
  Box,
  Flex,
  Grid,
  GridItem,
  Text,
  Input,
  Button,
  Divider,
} from "@chakra-ui/react";
import Image from "next/image";
import RegisterImage from "../assets/manRunning.jpg";
import Logo from "../assets/Logo.svg";
import { useRouter } from "next/router";
import { IoIosArrowBack } from "react-icons/io";
import { useState } from "react";
import { useUserContext } from "../context/UserContext";

const Register = () => {
  const { setEmailOnly } = useUserContext();
  const router = useRouter();
  const [error, setError] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [buttonClicked, setButtonClicked] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const fetchData = async () => {
    setError("");
    setErrorMessage("");

    try {
      const response = await fetch("http://localhost:5000/api/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        const errorMessage = await response.text();
        console.log("Erro ao enviar dados para o backend!");
        console.log(errorMessage);
        setError(errorMessage);
        throw new Error(errorMessage);
      }

      //Atualiza o estado do usuário com o email
      localStorage.setItem("email", email);
      setEmailOnly(email);

      router.push("/registration");
    } catch (error) {
      console.error("Erro ao enviar dados: ", error.message);
    }
  };

  const handleButtonClick = () => {
    if (email && password) {
      fetchData();
    } else {
      setErrorMessage("Por favor, preencha todos os campos.");
    }
    setButtonClicked(true);
  };

  console.log(error);

  return (
    <Box height="100vh">
      <Grid templateColumns="repeat(10, 1fr)">
        <GridItem colSpan={6} h="100vh" position="relative">
          <Image
            src={RegisterImage}
            alt="Image"
            layout="fill"
            objectFit="cover"
          />
        </GridItem>
        <GridItem colSpan={4} h="100vh" width="40vw">
          <Flex
            justifyContent="space-between"
            alignItems="center"
            margin="60px"
            marginTop="200px"
          >
            <Flex>
              <Image src={Logo} alt="Logo" />
            </Flex>
            <Flex
              cursor="pointer"
              alignItems="center"
              gap="10px"
              onClick={() => {
                router.push("/");
              }}
            >
              <IoIosArrowBack />
              <Text fontFamily="poppins.400" sx={{ textAlign: "center" }}>
                Voltar
              </Text>
            </Flex>
          </Flex>
          <Flex
            justifyContent="center"
            alignItems="center"
            w="inherit"
            direction="column"
            padding="48px"
            rowGap="48px"
            flexGrow={1}
          >
            <Box>
              <Text
                width="450px"
                fontFamily="inter.400"
                fontSize="sm"
                marginLeft="20px"
                marginBottom="10px"
              >
                E-mail
              </Text>
              <Input
                fontFamily="inter.400"
                placeholder="Digite seu e-mail"
                bgColor="offwhite"
                borderColor="white"
                h="48px"
                w="35vw"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
              />
            </Box>
            <Box>
              <Text
                width="450px"
                fontFamily="inter.400"
                fontSize="sm"
                marginLeft="20px"
                marginBottom="10px"
              >
                Senha
              </Text>
              <Input
                fontFamily="inter.400"
                placeholder="Digite sua senha"
                bgColor="offwhite"
                borderColor="white"
                h="48px"
                w="35vw"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
              />
              <Box py={2}>
                <Text color="red" fontSize="xs">
                  {error}
                </Text>
              </Box>
            </Box>
            <Box>
              <Button
                fontFamily="inter.400"
                bgColor="brand.900"
                w="35vw"
                textColor="white"
                onClick={handleButtonClick}
              >
                Criar conta
              </Button>
              {buttonClicked && errorMessage && (
                <Box py={2}>
                  <Text color="red" fontSize="xs">
                    {errorMessage}
                  </Text>
                </Box>
              )}
            </Box>
            <Divider color="gray" />
            <Box display="flex" alignItems="center">
              <Text fontFamily="inter.400" fontSize="sm">
                Ja tenho uma conta
              </Text>
              <Text
                fontFamily="inter.500"
                color="brand.900"
                marginLeft="10px"
                cursor="pointer"
                onClick={() => router.push("/login")}
              >
                Entrar
              </Text>
            </Box>
          </Flex>
        </GridItem>
      </Grid>
    </Box>
  );
};

export default Register;
