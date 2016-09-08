package example.customer.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import example.customer.service.util.StringUtils;

public class AWSEmailSender {
	
	private static final Logger LOG = LoggerFactory.getLogger(AWSEmailSender.class);
	
	@Value(value="${aws.access.key.id}")
	private String awsAccessKeyId;
	
	@Value(value="${aws.access.key}")
	private String awsAccessKey;
	
	public void sendEmail(String from, String to, String replyToEmail, String emailSubject, String emailBody){
		
		LOG.debug("sendEmail: begin");
				
		Destination dest = new Destination().withToAddresses(to);
      
        // Create the subject and body of the message.
        Content subject = new Content().withData(emailSubject);
        Content textBody = new Content().withData(emailBody);
        Body body = new Body().withText(textBody);
        
        Message message = new Message().withSubject(subject).withBody(body);
        
        // create the email request
        SendEmailRequest request = 
        		new SendEmailRequest().withSource(from).withDestination(dest).withMessage(message);
        
        if (!StringUtils.isStringEmpty(replyToEmail)){
        	request.withReplyToAddresses(replyToEmail);
        }
        
        try {
        	BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKeyId, awsAccessKey);
        	AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(awsCreds);
        	
        	// TODO change to your appropriate AWS region
        	Region region = Region.getRegion(Regions.US_EAST_1);
            client.setRegion(region);
       
            LOG.debug("sendEmail: attempt to send email");
            SendEmailResult result = client.sendEmail(request); 
            
            LOG.debug("sendEmail: email sent sucessfully, email result object: " + result.toString());
        	
        } catch(Exception ex){
        	String error = "ERROR:sendEmail: error attempting to send email " + ex.getMessage();
        	String details = "from: " + from + "\nto: " + to + "\n\nmessage:\n" + emailBody;
        	LOG.error(error);
        	LOG.debug(details);
        }
	}

}
