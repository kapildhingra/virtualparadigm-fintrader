package com.virtualparadigm.fintrader.app.chart.rest.impl.resource;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtualparadigm.fintrader.app.chart.rest.api.Chart;

@RestController
public class ChartController
{
	private static final String TEMPLATE = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	public ChartController()
	{
		System.out.println("starting greeting controller");
	}
	
//	@RequestMapping(method=RequestMethod.GET, value="/greeting")
	@GetMapping(value="/chart")
	public Chart getChart(@RequestParam(value="name", defaultValue="World") String name)
	{
		return new Chart(counter.incrementAndGet(), String.format(TEMPLATE, name));
	}
	
}