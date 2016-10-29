package space.kyu.wechat_.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wechat")
public class MusicController {
	@RequestMapping("/music/mymusic")
	public String getMyMusic() {
		return "music";
	}
}
