package com.proyecto_dbp.email;


import com.proyecto_dbp.user.domain.UserService;
import com.proyecto_dbp.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;


@Component
public class EmailListenerUser {

    @Autowired
    private EmailService emailService;

    @Autowired
    //private userService userService;
    private UserService userService;

    //private userResponseDto userResponseDto;
    private UserResponseDto userResponseDto;

    //Método que rellene los placeholders del html
    public String generarEmailHtml(String userId, String email, String nombre, String rol) {
        // Llamar a la imagen logo1.webp

        String emailHtml = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <meta charset='UTF-8'>\n" +
                "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "  <title>¡Hola {{nombre}} Bienvenid@ a FoodTales!</title>\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      background-color: #f4f4f4;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    .container {\n" +
                "      max-width: 600px;\n" +
                "      margin: 20px auto;\n" +
                "      background-color: #ffffff;\n" +
                "      padding: 20px;\n" +
                "      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "    }\n" +
                "    h1 {\n" +
                "      color: #333333;\n" +
                "    }\n" +
                "    h2 {\n" +
                "      color: #555555;\n" +
                "    }\n" +
                "    p {\n" +
                "      color: #777777;\n" +
                "    }\n" +
                "    .footer {\n" +
                "      text-align: center;\n" +
                "      margin-top: 20px;\n" +
                "      color: #999999;\n" +
                "      font-size: 12px;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class='container'>\n" +
                "    <section>\n" +
                "      <img src='cid:logo4' alt='FoodTales Logo' style='max-width: 100%; height: auto;'>\n" +
                "    </section>\n" +
                "    <h1>¡Hola {{nombre}}, bienvenid@ a FoodTales!</h1>\n" +
                "    <h2>Tu registro ha sido exitoso</h2>\n" +
                "    <img src=\"https://i.imgur.com/y1nOX2v.png\" alt=\":)\">" +
                "    <p>Te has registrado con este email: <strong>{{email}}</strong></p>\n" +
                "    <p>Te has registrado con el rol de: <strong>{{rol}}</strong></p>\n" +
                "    <p>¡Comparte con la comunidad tu preferencia en comidas!</p>\n" +
                "    <div class='footer'>\n" +
                "      <p>&copy; 2024 FoodTales. Todos los derechos reservados.</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";

        emailHtml = emailHtml.replace("{{userId}}", userId)
                .replace("{{nombre}}", nombre)
                .replace("{{email}}", email)
                .replace("{{rol}}", rol);

        return emailHtml;
    }


    @EventListener
    @Async
    public void handleHelloEmailEvent(HelloEmailEvent event) throws MessagingException, jakarta.mail.MessagingException {

        String contenidoTxt = "mensaje simple en formato string - no html";

        String destinatario = event.getEmail(); // Puedes extraer esto del userResponseDto si lo necesitas

        String asunto = "Bienvenid@ a FoodTales - Registro Exitoso";


        String contenidoHTML = generarEmailHtml(event.getUserId().toString(), event.getEmail(),event.getName(), event.getRol());

        emailService.enviarCorreoHTML(destinatario,asunto,contenidoHTML);

    }
}