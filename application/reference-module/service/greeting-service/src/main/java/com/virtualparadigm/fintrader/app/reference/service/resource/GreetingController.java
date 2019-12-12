package com.virtualparadigm.fintrader.app.reference.service.resource;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtualparadigm.fintrader.app.reference.shared.dto.Greeting;

@RestController
public class GreetingController
{
	private static final String TEMPLATE = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	public GreetingController()
	{
		System.out.println("starting greeting controller");
	}
	
//	@RequestMapping(method=RequestMethod.GET, value="/greeting")
	@RequestMapping(value="/greeting")
	public Greeting getGreeting(@RequestParam(value="name", defaultValue="World") String name)
	{
		return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
	}
	
}