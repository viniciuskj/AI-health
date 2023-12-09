package com.server.configurations;

import java.io.*;
import java.net.Socket;
import java.util.Calendar;
import com.server.errors.CustomException;
import com.server.utils.Data;

public class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine = in.readLine();
            if (inputLine.startsWith("CRED:")) {
                try {
                    String credentials = inputLine.substring(5); // Remove "CRED:"
                    validateCredentials(credentials);
                    out.println("valido");
                } catch (CustomException e) {
                    out.println("erro:" + e.getMessage());
                }
            } else if (inputLine.startsWith("USER:")) {
                try {
                    String userProfile = inputLine.substring(5); // Remove "USER:"
                    validateUserData(userProfile);
                    out.println("sucesso");
                } catch (CustomException e) {
                    out.println("erro:" + e.getMessage());
                }

            } else {
                out.println("Formato de mensagem desconhecido");
            }

        } catch (IOException e) {
            System.out.println("Erro ao interagir com o cliente: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Erro ao fechar o socket do cliente: " + e.getMessage());
            }
        }
    }

    // Validação de credenciais
    private void validateCredentials(String credentials) throws CustomException {
        // Espera-se que as credenciais venham no formato "email:senha"
        String[] parts = credentials.split(":");
        if (parts.length != 2) {
            throw new CustomException("Formato de credenciais inválido.", 1002);
        }

        String email = parts[0];
        String password = parts[1];

        if (!isValidEmail(email)) {
            throw new CustomException("Endereço de e-mail inválido.", 1007);
        }

        if (!isPasswordStrong(password)) {
            throw new CustomException(
                    "Para atender aos critérios de segurança, sua senha de conter pelo menos uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.",
                    1001);
        }
    }

    private boolean isValidEmail(String email) {
        // Implementação da validação de e-mail
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isPasswordStrong(String password) {
        // Implementação da validação da força da senha
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }

    // Validação completa do usuário
    private void validateUserData(String userProfile) throws CustomException {
        // Espera-se que as credenciais venham no formato "name:dateOfBirth:..."

        String[] parts = userProfile.split(":");
        if (parts.length != 4) {
            throw new CustomException("Formato de usuário inválido.", 1002);
        }

        String dateOfBirth = parts[0];
        String weight = parts[1];
        String height = parts[2];
        String exerciseTime = parts[3];

        Data birthDate = convertDateString(dateOfBirth);
        // Verificar se a data é válida
        if (!Data.isValida(birthDate.getDia(), birthDate.getMes(), birthDate.getAno())) {
            throw new CustomException(
                    "Data de nascimento inválida.",
                    1003);
        }

        // Verificar se é adulto
        if (!isAdult(birthDate)) {
            throw new CustomException(
                    "O usuário deve ter mais de 18 anos.", 1004);
        }
        if (Double.parseDouble(weight) <= 0) {
            throw new CustomException(
                    "O usuário deve ter mPeso inválido.", 1005);
        }

        if (Integer.parseInt(height) <= 0) {
            throw new CustomException("Altura inválida.", 1006);
        }

        int exerciseTimeInt = Integer.parseInt(exerciseTime);
        if (exerciseTimeInt < 0 || exerciseTimeInt > 1440) {
            throw new CustomException("Tempo de exercício deve ser menor ou igual a 1440 minutos.", 1008);
        }
    }

    // Verifica se é a idade do usuário é maior ou igual a 18 anos
    private boolean isAdult(Data birthDate) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.set(birthDate.getAno(), birthDate.getMes() - 1, birthDate.getDia());

        int age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        if (currentCalendar.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                (currentCalendar.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                        currentCalendar.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
            age--; // Ainda não fez aniversário neste ano
        }

        return age >= 18;
    }

    // Conversão de string de data para Data
    private Data convertDateString(String dateString) {
        try {
            String[] parts = dateString.split("/");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Formato de data inválido.");
            }

            byte day = Byte.parseByte(parts[0]);
            byte month = Byte.parseByte(parts[1]);
            short year = Short.parseShort(parts[2]);

            return new Data(day, month, year);
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter a data: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Erro ao criar objeto Data: " + e.getMessage());
            return null;
        }
    }
}
