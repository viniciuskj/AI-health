import React, { useEffect, useState } from "react";
import { Flex, Heading, Text, Button, Box } from "@chakra-ui/react";
import Logo from "../assets/Logo.svg";
import Image from "next/image";
import { useRouter } from "next/router";

const Navbar = () => {
  const [scrolling, setScrolling] = useState(false);

  const router = useRouter();

  const handleScroll = () => {
    if (window.scrollY > 50) {
      setScrolling(true);
    } else {
      setScrolling(false);
    }
  };

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  const navbarColor = scrolling ? "rgba(255, 255, 255, 0.8)" : "transparent";

  return (
    <Flex
      as="nav"
      align="center"
      justify="space-between"
      wrap="wrap"
      paddingX="50px"
      paddingY="30px"
      bg={navbarColor}
      color="white"
      position="fixed"
      top="0"
      left="0"
      right="0"
      zIndex="999"
      transition="background-color 0.3s"
    >
      <Image src={Logo} alt="Logo" />
      <Flex align="center">
        <Button
          fontFamily="inter.500"
          color="black"
          onClick={() => router.push("/login")}
        >
          Entrar
        </Button>
        <Button
          fontFamily="inter.500"
          color="brand.900"
          backgroundColor="black"
          width="150px"
          onClick={() => router.push("/register")}
        >
          Criar conta
        </Button>
      </Flex>
    </Flex>
  );
};

export default Navbar;
