package AppRun.Configs;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	
	@Bean
	SimpleMailMessage getsimplemail()
	{
		return new SimpleMailMessage();
	}
	 @Bean
	 JavaMailSenderImpl getMailSender()
	 {
	        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

	        javaMailSender.setHost("smtp.gmail.com");
	        javaMailSender.setPort(587);
	        javaMailSender.setUsername("paymentwallet83@gmail.com");
	        javaMailSender.setPassword("sdeqmqqqwqyzwbjh");

	        Properties properties = javaMailSender.getJavaMailProperties();
	        properties.put("mail.debug", true);

	        properties.put("mail.smtp.starttls.enable", true);

	        return javaMailSender;

	    }

}
