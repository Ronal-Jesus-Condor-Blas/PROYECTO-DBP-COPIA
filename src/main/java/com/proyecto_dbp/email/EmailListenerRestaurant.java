package com.proyecto_dbp.email;

import com.proyecto_dbp.restaurant.domain.RestaurantService;
import com.proyecto_dbp.restaurant.dto.RestaurantResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;


@Component
public class EmailListenerRestaurant  {

    @Autowired
    private EmailService emailService;

    @Autowired
    //private restaurantService restaurantService;
    private RestaurantService restaurantService;

    //private restaurantResponseDto restaurantResponseDto;
    private RestaurantResponseDto restaurantResponseDto;

    //Método que rellene los placeholders del html
    public String generarEmailHtml(String restaurantId, String email, String nombre, String location) {
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
                "    <h2>Tu registro como empresa ha sido exitoso</h2>\n" +
                "    <p>Tu restaurantId es: <strong>{{userId}}</strong></p>\n" +
                "    <p>Te has registrado con este email: <strong>{{email}}</strong></p>\n" +
                "    <p>Tu ubicación actual es: <strong>{{location}}</strong></p>\n" +
                "    <p>¡Comparte con la comunidad tu preferencia en comidas!</p>\n" +
                "    <div class='footer'>\n" +
                "      <p>&copy; 2024 FoodTales. Todos los derechos reservados.</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";

        emailHtml = emailHtml.replace("{{userId}}", restaurantId)
                .replace("{{nombre}}", nombre)
                .replace("{{email}}", email)
                .replace("{{location}}", location);

        return emailHtml;
    }


    @EventListener
    @Async
    public void handleHelloEmailEvent(NewRestaurantEvent event) throws MessagingException, jakarta.mail.MessagingException {

        String contenidoTxt = "mensaje simple en formato string - no html";
        //CODE ADICIONAL PARA ENVIAR HTML
        // Preparar contenido del correo
        String destinatario = event.getEmail(); // Puedes extraer esto del restaurantResponseDto si lo necesitas
        String asunto = "Bienvenid@ a FoodTales - Registro Exitoso";
        String contenidoHTML = generarEmailHtml(event.getRestaurantId().toString(), event.getEmail(),event.getName(), event.getLocation());

        // Enviar el correo usando el EmailService - usa SimpleMessage - funciona
        //emailService.sendSimpleMessage(destinatario, asunto, contenidoTxt);

        //Enviar correo usando mimemessage
        emailService.enviarCorreoHTML(destinatario,asunto,contenidoHTML);
        //FIN DE CODE ADICIONAL
    }
}