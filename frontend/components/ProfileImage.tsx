import Image from "next/image";
import { Box } from "@chakra-ui/react";

const ProfileImage = ({ src }) => {
  return (
    <Box height="56px" width="56px" borderRadius="full" overflow="hidden" position="relative">
      <Image src={src} alt="Profile" layout="fill" objectFit="cover" />
    </Box>
  );
};

export default ProfileImage;