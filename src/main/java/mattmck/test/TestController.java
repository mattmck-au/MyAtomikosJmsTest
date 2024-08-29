package mattmck.test;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {

	@Autowired
    private JmsTemplate jmsTemplate;

	@Autowired()
	@Qualifier("xaJmsTemplate")
	private JmsTemplate xaJmsTemplate;

	
	@GetMapping("/test")
	//@Transactional
	public String test() {
		jmsTemplate.convertAndSend("testNonXaQueue", "TEST NON XA");
		return "TEST NON XA";
	}
	
	@GetMapping("/testxa")
	@Transactional
	public String testxa() {
		xaJmsTemplate.convertAndSend("testXaQueue", "TEST XA");
		return "TEST XA";
	}
}