import {
  Box,
  Button,
  Flex,
  IconButton,
  Input,
  Spinner,
  Text,
} from "@chakra-ui/react";
import { FiLogOut } from "react-icons/fi";
import { useEffect, useState } from "react";
import { InfoIcon } from "@chakra-ui/icons";
import { useRouter } from "next/router";
import ProfileImage from "../components/ProfileImage";
import avatar from "../assets/avatar.jpg";

const Diagnostic = () => {
  const [symptoms, setSymptoms] = useState("");
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(false);
  const router = useRouter();

  useEffect(() => {
    if (typeof window !== "undefined") {
      // Evita acessar localStorage durante a renderização do lado do servidor (SSR - Server-Side Rendering)
      const token = localStorage.getItem("jwtToken");

      const fetchDiagnoses = async () => {
        try {
          const response = await fetch(
            "http://localhost:5000/profile/diagnosis",
            {
              method: "GET",
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );

          if (!response.ok) {
            const errorMessage = await response.text();
            console.error("Erro ao enviar dados para o backend!", errorMessage);
            return;
          }

          const getResponse = await response.json();
          setUserData(getResponse);
          console.log("GetResponse: ", getResponse);
        } catch (error) {
          console.error("Erro ao buscar diagnósticos", error);
        }
      };

      fetchDiagnoses();
    }
  }, []);

  const newDiagnosis = async () => {
    setLoading(true);
    const token = localStorage.getItem("jwtToken");
    let diagnosticId = null;

    try {
      const response = await fetch("http://localhost:5000/profile/diagnosis", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ symptoms }),
      });

      if (!response.ok) {
        const errorMessage = await response.json();
        console.error("Erro ao enviar dados para o backend!", errorMessage);
        return;
      }

      const data = await response.json();
      diagnosticId = data.id;
    } catch (error) {
      console.error("Erro ao criar diagnósticos", error);
    } finally {
      setLoading(false);
      if (diagnosticId) {
        router.push(`diagnostic/${diagnosticId}`);
      }
    }
  };

  return (
    <Box backgroundColor="offwhite" h="100vh">
      {loading ? (
        <Flex
          justifyContent="center"
          alignItems="center"
          h="inherit"
          flexDirection="column"
        >
          <Spinner size="lg" />
          <Text mt="10px" fontFamily="inter.500">
            Gerando diagnóstico
          </Text>
        </Flex>
      ) : (
        <Box paddingTop="100px" marginX="310px" justifyContent="center">
          <Box display="flex" gap="12px" justifyContent="space-between">
            <Flex align="center" gap="15px">
              <ProfileImage src={avatar} />
              <Box>
                <Text fontFamily="poppins.400" fontSize="lg">
                  {userData ? userData?.name : "Synchronize"}
                </Text>
                <Text
                  fontFamily="poppins.400"
                  color="gray"
                  cursor="pointer"
                  onClick={() => {
                    router.push("/dashboard");
                  }}
                >
                  Ver perfil
                </Text>
              </Box>
            </Flex>
            <Flex
              align="center"
              gap="10px"
              onClick={() => {
                router.push("/login");
              }}
            >
              <Text fontFamily="poppins.400" cursor="pointer">
                Ir embora
              </Text>
              <FiLogOut />
            </Flex>
          </Box>
          <Box
            marginTop="65px"
            backgroundColor="black"
            height="100px"
            borderRadius="8px"
            padding="20px"
            display="flex"
            justifyContent="space-between"
          >
            <Box>
              <Text color="white" fontFamily="inter.500" marginBottom="10px">
                Um guia para te situar
              </Text>
              <Text color="gray" fontSize="14px" fontFamily="inter.400">
                Descreva o que estiver sentindo e você receberá um relatório
                inteligente e instantâneo para ter uma base do que poderia estar
                ocorrendo.
              </Text>
            </Box>
            <IconButton
              isRound={true}
              variant="solid"
              colorScheme="white"
              aria-label="Done"
              fontSize="20px"
              icon={<InfoIcon />}
            />
          </Box>
          <Text
            fontFamily="inter.400"
            fontSize="4xl"
            width="500px"
            marginTop="45px"
          >
            Então... me diga, o que você está sentindo?
          </Text>
          <Input
            borderColor="black"
            borderWidth="2px"
            height="40px"
            width="50%"
            fontSize="14px"
            fontFamily="inter.400"
            placeholder="Dor de cabeça, dores nas costas..."
            marginBottom="15px"
            marginTop="70px"
            value={symptoms}
            onChange={(event) => setSymptoms(event.target.value)}
          />
          <Box>
            <Button
              fontFamily="inter.500"
              color="brand.900"
              backgroundColor="black"
              width="150px"
              onClick={newDiagnosis}
            >
              Enviar
            </Button>
          </Box>
          <Text color="gray" fontSize="sm" width="60%" marginTop="220px">
            * Para obter um diagnóstico preciso e recomendações específicas, é
            importante consultar um médico. Além disso, se os sintomas
            persistirem ou piorarem, é essencial buscar atendimento médico
            imediatamente.
          </Text>
        </Box>
      )}
    </Box>
  );
};

export default Diagnostic;
