import { Box, Flex, IconButton, Text } from "@chakra-ui/react";
import { FiLogOut } from "react-icons/fi";
import { useEffect, useState } from "react";
import { InfoIcon } from "@chakra-ui/icons";
import { useRouter } from "next/router";
import ReportCard from "../../components/ReportCard";
import ProfileImage from "../../components/ProfileImage";
import avatar from "../../assets/avatar.jpg";

const Diagnostic = () => {
  const [userData, setUserData] = useState(null);
  const router = useRouter();

  const id = router.query.diagnosticId;

  useEffect(() => {
    if (typeof window !== "undefined") {
      // Evita acessar localStorage durante a renderização do lado do servidor (SSR - Server-Side Rendering)
      const token = localStorage.getItem("jwtToken");

      const fetchDiagnoses = async () => {
        try {
          console.log(id);
          const response = await fetch(
            `http://localhost:5000/profile/diagnosis/${id}`,
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

          const GetResponse = await response.json();
          setUserData(GetResponse);
          console.log("GetResponse: ", GetResponse);
        } catch (error) {
          console.error("Erro ao buscar diagnósticos", error);
        }
      };

      fetchDiagnoses();
    }
  }, [id]);

  return (
    <Box backgroundColor="offwhite" h="100vh">
      <Box paddingTop="100px" marginX="310px" justifyContent="center">
        <Box display="flex" gap="12px" justifyContent="space-between">
          <Flex align="center" gap="15px">
            <ProfileImage src={avatar} />
            <Box>
              <Text fontFamily="poppins.400" fontSize="lg">
                {userData ? userData?.userInfo?.name : "Synchronize"}
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

        <Box marginTop="50px" gap="70px">
          <Text fontFamily="inter.500" fontSize="2xl">
            Descritivo
          </Text>
          {userData &&
            userData?.diagnoses?.report.map((item: any, index: number) => (
              <ReportCard
                key={index}
                problem={item.problem}
                chanceOfOccurrence={item.chanceOfOccurrence}
                description={item.description}
              />
            ))}
        </Box>
      </Box>
    </Box>
  );
};

export default Diagnostic;
