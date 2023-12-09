import {
  Box,
  Flex,
  Text,
  SimpleGrid,
  Button,
  Input,
  Card,
} from "@chakra-ui/react";
import ProgressBar from "../components/ProgressBar";
import Step from "../components/Step";
import SectionTitle from "../components/SectionTitle";
import { ArrowForwardIcon } from "@chakra-ui/icons";
import PlanCard from "../components/PlanCard";
import DateCard from "../components/DateCard";
import Navbar from "../components/Navbar";
import Womam from "../assets/woman.png";
import Image from "next/image";
import ProfileImage from "../components/ProfileImage";
import PlayerButtom from "../assets/player-buttom.svg";

const IndexPage = () => {
  return (
    <>
      <Navbar />
      <SimpleGrid columns={2}>
        <Box height="100vh" display="flex" alignItems="center" bg="offwhite">
          <Box margin="100px">
            <Text
              fontSize="7xl"
              width="450px"
              fontFamily="poppins.500"
              paddingBottom="60px"
            >
              Diagnóstico inteligente com IA.
            </Text>
            <Text fontWeight="inter.400" paddingBottom="18px">
              Diga o que está sentindo
            </Text>
            <Input
              borderColor="black"
              borderWidth="2px"
              placeholder="Dor de cabeça, dores nas costas..."
              marginBottom="10px"
            />
            <Button
              fontFamily="inter.500"
              color="brand.900"
              backgroundColor="black"
              width="150px"
              marginBottom="115px"
            >
              Enviar
            </Button>

            <Text
              color="gray"
              width="450px"
              fontWeight="inter.400"
              fontSize="sm"
            >
              * Para obter um diagnóstico preciso e recomendações específicas, é
              importante consultar um médico. Além disso, se os sintomas
              persistirem ou piorarem, é essencial buscar atendimento médico
              imediatamente.
            </Text>
          </Box>
        </Box>
        <Box
          height="100vh"
          display="flex"
          alignItems="center"
          justifyContent="center"
          bgColor="brand.900"
        >
          <Box>
            <SimpleGrid columns={3} spacing="150px">
              <ProgressBar height="50vh" percentage={30} reason="Enxaqueca" />
              <ProgressBar
                height="50vh"
                percentage={50}
                reason="Má alimentação"
              />
              <ProgressBar
                height="50vh"
                percentage={60}
                reason="Falta de exercício"
              />
            </SimpleGrid>
          </Box>
        </Box>
      </SimpleGrid>

      {/* Segunda sessão */}
      <Box height="100vh">
        <SectionTitle
          mainTitle={
            "Explore seu painel de controle com tudo o que você precisa"
          }
          subtitle={"Crie sua conta"}
        />

        <Box display="flex" justifyContent="center" alignContent="center">
          <SimpleGrid columns={3} spacing={20} alignItems="flex-end">
            <Box
              position="relative"
              width="300px"
              height="410px"
              overflow="hidden"
              borderRadius="3xl"
              bg="gray.200"
            >
              <Image src={Womam} alt="Woman" />
              <Box
                position="absolute"
                bottom="4"
                left="4"
                right="4"
                paddingY="2"
                paddingX="4"
                bg="blackAlpha.600"
                borderRadius="2xl"
                display="flex"
                alignItems="center"
                justifyContent="center"
              >
                <Box
                  display="flex"
                  alignItems="center"
                  justifyContent="center"
                  gap="1rem"
                  width="full"
                  height="full"
                >
                  <Image src={PlayerButtom} alt="Player Button" />
                  <Text color="white" fontSize="md" fontFamily="poppins.400">
                    Informações pessoais
                  </Text>
                </Box>
              </Box>
            </Box>
            <Box minWidth="400px">
              <Card
                width="280px"
                height="140px"
                bgColor="card"
                borderRadius="2xl"
                padding="10px"
                ms="auto"
                marginBottom="50px"
              >
                <Card height="80px" bg="black" borderRadius="xl" padding="10px">
                  <Flex
                    justifyContent="center"
                    alignContent="center"
                    alignItems="center"
                    gap="20px"
                    height="inherit"
                  >
                    <ProfileImage src={Womam} />
                    <Text color="white" fontSize="lg" fontFamily="poppins.400">
                      Sophia Iwara
                    </Text>
                  </Flex>
                </Card>
                <Flex
                  justifyContent="center"
                  fontFamily="poppins.400"
                  fontSize="14px"
                  alignItems="center"
                  height="inherit"
                >
                  <Text>Meus Dados</Text>
                </Flex>
              </Card>
              <Card
                width="400px"
                height="210px"
                bgColor="card"
                borderRadius="2xl"
                padding="40px"
              >
                <Flex>
                  <Text fontSize="3xl" fontFamily="poppins.500" width="200px">
                    Histórico de doenças na família
                  </Text>
                  <Box
                    justifyContent="center"
                    gap="20px"
                    display="flex"
                    flexDirection="column"
                    ms="auto"
                  >
                    <Flex alignItems="center">
                      <Box
                        width="32px"
                        height="32px"
                        border="1px"
                        borderRadius="8px"
                        color="black"
                        marginRight="10px"
                      />
                      <Text fontFamily="poppins.500">Sim</Text>
                    </Flex>
                    <Flex alignItems="center">
                      <Box
                        width="32px"
                        height="32px"
                        border="1px"
                        borderRadius="8px"
                        bgColor="black"
                        color="black"
                        marginRight="10px"
                      />
                      <Text fontFamily="poppins.500">Não</Text>
                    </Flex>
                  </Box>
                </Flex>
              </Card>
            </Box>
            <Card
              width="300px"
              height="350px"
              bgColor="card"
              borderRadius="3xl"
              padding="20px"
            >
              <Text
                fontSize="xl"
                width="140px"
                marginTop="20px"
                marginBottom="30px"
                fontFamily="poppins.500"
              >
                Últimos diagnósticos
              </Text>
              <Box justifyContent="center">
                <Card
                  marginBottom="20px"
                  width="100%"
                  height="90px"
                  bgColor="black"
                  borderRadius="2xl"
                  alignContent="center"
                  padding="15px"
                >
                  <Box
                    display="flex"
                    alignItems="center"
                    justifyContent="left"
                    gap="15px"
                  >
                    <DateCard month="out" day="29" current={true} />
                    <Box>
                      <Text
                        color="white"
                        fontSize="sm"
                        fontFamily="poppins.400"
                      >
                        Dores de cabeça...
                      </Text>
                      <Text
                        fontSize="12px"
                        fontFamily="poppins.400"
                        color="gray"
                        cursor="pointer"
                      >
                        Ver detalhes <ArrowForwardIcon />
                      </Text>
                    </Box>
                  </Box>
                </Card>

                <Card
                  marginBottom="20px"
                  width="100%"
                  height="90px"
                  bgColor="black"
                  borderRadius="2xl"
                  alignContent="center"
                  padding="15px"
                >
                  <Box
                    display="flex"
                    alignItems="center"
                    justifyContent="left"
                    gap="15px"
                  >
                    <DateCard month="out" day="22" current={false} />

                    <Box>
                      <Text
                        color="white"
                        fontSize="sm"
                        fontFamily="poppins.400"
                      >
                        Dores nas pernas...
                      </Text>
                      <Text
                        fontSize="12px"
                        fontFamily="poppins.400"
                        color="gray"
                        cursor="pointer"
                      >
                        Ver detalhes <ArrowForwardIcon />
                      </Text>
                    </Box>
                  </Box>
                </Card>
              </Box>
            </Card>
          </SimpleGrid>
        </Box>
      </Box>

      {/* Terceira sessão */}
      <Box height="70vh" bgColor="offwhite">
        <SectionTitle
          mainTitle={
            "Seu guia para dar o próximo passo rumo a uma vida + saudável!"
          }
          subtitle={"Como funciona?"}
        />
        <Box display="flex" justifyContent="center" alignContent="center">
          <SimpleGrid columns={3} spacing="150px">
            <Step number={1} label="Conte um pouco sobre você" />
            <Step number={2} label="Diga o que está sentindo" />
            <Step number={3} label="Receba um diagnóstico" />
          </SimpleGrid>
        </Box>
        <Box
          display="flex"
          justifyContent="center"
          alignContent="center"
          textAlign="center"
        >
          <Text color="gray" width="820px" marginTop="140px">
            * Para obter um diagnóstico preciso e recomendações específicas, é
            importante consultar um médico. Além disso, se os sintomas
            persistirem ou piorarem, é essencial buscar atendimento médico
            imediatamente.
          </Text>
        </Box>
      </Box>
      {/* Quarta sessão */}

      <Box height="100vh">
        <SectionTitle
          mainTitle={
            "Obtenha diagnósticos personalizados, moldados por você e para você"
          }
          subtitle={"Vá rumo à saúde e bem-estar com outra perspectiva"}
        />
        <Box display="flex" justifyContent="center" alignContent="center">
          <SimpleGrid columns={2} spacing={14}>
            <PlanCard
              name="Básico"
              price="5"
              features={[
                "2 diagnósticos por mês",
                "Relatórios em gráficos",
                "Suporte 24/7",
              ]}
              subtitle={
                <Text fontFamily="poppins.500" marginBottom="30px" color="gray">
                  Experimente o quanto quiser
                </Text>
              }
              color="offwhite"
            />
            <PlanCard
              name="Premium"
              price="15"
              features={[
                "10 diagnósticos por mês",
                "Relatórios em gráficos",
                "Suporte 24/7",
              ]}
              subtitle={
                <Text
                  fontFamily="poppins.500"
                  marginBottom="30px"
                  color="brand.600"
                >
                  Experimente o quanto quiser *5
                </Text>
              }
              color="brand.900"
            />
          </SimpleGrid>
        </Box>
      </Box>
      <footer
        style={{
          backgroundColor: "black",
          color: "white",
          padding: "20px",
          textAlign: "left",
          height: "100px",
          display: "flex",
          alignItems: "center",
        }}
      >
        <Text fontFamily="poppins.400" marginLeft="30px">
          &copy; 2023 Synchronize | Todos os direitos reservados.
        </Text>
      </footer>
    </>
  );
};

export default IndexPage;
