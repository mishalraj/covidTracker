package io.mishal.coronavirus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.mishal.coronavirus.models.LocationStats;
import io.mishal.coronavirus.services.CoronaDataService;

@Controller
public class MainController {
	
	@Autowired
	CoronaDataService coronaDataServ;
	
	@GetMapping("/")
	public String home(Model model) {
		List<LocationStats> allStats = coronaDataServ.getAllStats();
		
		int totalReportedCases= allStats.stream().mapToInt(stat->stat.getCases()).sum();
		int totalNewCases= allStats.stream().mapToInt(stat->stat.getNewCases()).sum();
		
		model.addAttribute("locationStats",allStats);
		model.addAttribute("totalReportedCases",totalReportedCases);
		model.addAttribute("totalNewCases",totalNewCases);
	
		return "home";
	}

}
