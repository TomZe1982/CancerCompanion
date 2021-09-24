package de.tomze.backend.mail;


public class StartMailSender {

    public static void main(String [] args) {

        String userName = "3bcb50dc52e7d5";
        String password = "c1cf597480f226";

        MailSender sender = new MailSender();
        sender.login("smtp.mailtrap.io", "465", userName, password);
        try {
            sender.send("6e8e6f3970-ebc11d@inbox.mailtrap.io", "Tomze",
                    "6e8e6f3970-ebc11d@inbox.mailtrap.io", "Test", "Eiersalat ist gut");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
