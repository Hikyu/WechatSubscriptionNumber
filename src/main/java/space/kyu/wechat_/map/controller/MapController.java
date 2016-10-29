package space.kyu.wechat_.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 地图功能
 * 
 * @author yukai
 *
 */
@Controller
@RequestMapping("/wechat")
public class MapController {
	@RequestMapping("/map/showRestaurant")
	public String showRestaurant(@RequestParam(value = "location_x", required = true) String location_x,
			@RequestParam(value = "location_y", required = true) String location_y,
			@RequestParam(value = "label", required = true) String label, Model model) {
		model.addAttribute("locationx", location_x);
		model.addAttribute("locationy", location_y);
		model.addAttribute("label",label);
		return "hello";
	}

}
