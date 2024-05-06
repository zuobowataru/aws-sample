package aws.com.example.domain.ec2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class Ec2Info {

    @Value("${ami-id:N/A}")
    private String amiId;

    @Value("${hostname:N/A}")
    private String hostname;
    

    @Value("${public-ipv4:N/A}")
    private String publicIpv4;

    @Value("${instance-type:N/A}")
    private String instanceType;

    @Value("${instance-id:N/A}")
    private String instanceId;

    // parameter store 用
    @Value("${param.test:N/A}")
    private String paramTest;

    // JSON 用
    public String toJson() throws JsonProcessingException{
    	ObjectMapper mapper = new ObjectMapper();
    	return mapper.writeValueAsString(this);
    }
}
