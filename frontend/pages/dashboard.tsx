import { Box, Button, Flex, Text } from "@chakra-ui/react";
import { FiLogOut } from "react-icons/fi";
import { useRouter } from "next/router";
import React, { useEffect, useState } from "react";
import { useUserContext } from "../context/UserContext";
import HistoryCard from "../components/HistoryCard";
import ProfileImage from "../components/ProfileImage";
import avatar from "../assets/avatar.jpg";

const Dashboard = () => {
  const router = useRouter();
  const [userData, setUserData] = useState(null);
  const { logout } = useUserContext();

  useEffect(() => {
    if (typeof window !== "undefined") {
      // Evita acessar localStorage durante a renderização do lado do servidor (SSR - Server-Side Rendering)
      const token = localStorage.getItem("jwtToken");
      console.log(token);

      const fetchDiagnoses = async () => {
        try {
          const response = await fetch("http://localhost:5000/api/dashboard", {
            method: "GET",
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });

          if (!response.ok) {
            const errorMessage = await response.text();
            console.error("Erro ao enviar dados para o backend!", errorMessage);
            return;
          }

          const data = await response.json();
          setUserData(data);
          console.log("userData: ", userData);
        } catch (error) {
          console.error("Erro ao buscar diagnósticos", error);
        }
      };

      fetchDiagnoses();
    }
  }, []);

  const handleLogout = () => {
    logout(); // Limpa o estado do usuário e remove o token
    router.push("/login");
  };

  const handleDiagnostic = (diagnosisId: string) => {
    // console.log("userData: ", userData);
    router.push(`/diagnostic/${diagnosisId}`);
  };

  // Função para formatar o mês
  function formatMonth(monthNumber: any) {
    const months = [
      "jan",
      "fev",
      "mar",
      "abr",
      "mai",
      "jun",
      "jul",
      "ago",
      "set",
      "out",
      "nov",
      "dez",
    ];
    return months[monthNumber];
  }

  // Função para extrair dia e mês de uma string de data
  function extractDayAndMonth(dateString: any) {
    const date = new Date(dateString);
    const day = date.getDate(); // Obtém o dia
    const month = formatMonth(date.getMonth()); // Obtém o mês (0-11)
    return { day, month };
  }

  return (
    <Box backgroundColor="offwhite" h="100vh">
      <Box paddingTop="100px" marginX="310px" justifyContent="center">
        <Box display="flex" gap="12px" justifyContent="space-between">
          <Flex align="center" gap="15px">
            <ProfileImage src={avatar}/>
            <Box>
              <Text fontFamily="poppins.400" fontSize="lg">
                {userData ? userData?.userInfo?.name : "Synchronize"}
              </Text>
            </Box>
          </Flex>
          {/* <Text
            fontFamily="poppins.400"
            color="gray"
            cursor="pointer"
            onClick={() => {
              router.push("/profile");
            }}
          >
            Meus dados
          </Text> */}
          <Flex align="center" gap="10px">
            <Text
              fontFamily="poppins.400"
              cursor="pointer"
              onClick={handleLogout}
            >
              Ir embora
            </Text>
            <FiLogOut />
          </Flex>
        </Box>
        <Flex justifyContent="space-between" marginTop="120px">
          <Text fontFamily="poppins.500" fontSize="2xl">
            Últimos diagnósticos
          </Text>
          <Button
            fontFamily="inter.500"
            color="brand.900"
            backgroundColor="black"
            width="200px"
            marginBottom="115px"
            onClick={() => {
              router.push("/diagnostic");
            }}
          >
            Fazer diagnóstico
          </Button>
        </Flex>
        {userData?.diagnoses?.map((diagnosis: any, index: number) => {
          const { day, month } = extractDayAndMonth(diagnosis.currentDate);
          return (
            <HistoryCard
              key={index}
              day={day.toString()}
              month={month}
              symptoms={diagnosis.symptoms}
              onclick={() => handleDiagnostic(diagnosis.diagnosisId)}
            />
          );
        })}
      </Box>
    </Box>
  );
};

export default Dashboard;
