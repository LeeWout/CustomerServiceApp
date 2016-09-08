package example.customer.service.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import example.customer.service.util.AWSEmailSender;
import example.customer.service.model.ContactUsModel;

@RestController
@RequestMapping("/cs")
public class CustomerServiceController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceController.class);
	
	@Autowired
	private AWSEmailSender awsEmailSender;
	
	@Value(value="${support.email}")
	private String supportEmail;
	
	@RequestMapping(value="/", method=RequestMethod.POST) 
    public void sendEmail(@RequestBody ContactUsModel customerInfo){
		LOG.debug("sendEmail: begin");

		// TODO change the below email bodies and subjects however you see fit... 
		// TODO Also may want to use a StringBuilder instead of just strings.
		
		String emailBody = "Customer Service Message\n\n" + "Customer name: " + customerInfo.getFirstName() + " " + customerInfo.getLastName() +
				"\n" + "Email: " + customerInfo.getEmail() + "\n" + "Phone: " + customerInfo.getPhone() + "\n" + 
				"Customer type: " + customerInfo.getType() + "\n" + "Reason: " + customerInfo.getReason() + "\n" + 
				"Message:\n" + customerInfo.getMessage();

		LOG.debug("sendEmail: sending customer service message");
		awsEmailSender.sendEmail(supportEmail, supportEmail, customerInfo.getEmail(),
				"Customer Service Message", emailBody);
		
		// send auto-reply email
		String autoReplyEmailBody = "Hello " + customerInfo.getFirstName() + " " + customerInfo.getLastName() + 
				",\n\n" +
				"We have received your \'Contact Us\' request and will review it shortly. If follow up is required " +
				"we will contact you as soon as possible.\n\n" +
				"Have a great day!";
				
		awsEmailSender.sendEmail(supportEmail, customerInfo.getEmail(), null,
				"Customer service request received", autoReplyEmailBody);
		LOG.debug("sendEmail: sent auto-reply email");
	}

}
