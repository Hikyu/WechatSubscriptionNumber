package space.kyu.wechat_.music.service;

import org.springframework.stereotype.Service;

import space.kyu.wechat_.common.service.AbstractTextMsgService;

@Service
public class MusicService extends AbstractTextMsgService{
	@Override
	protected String dealwithRequest(String openid, String content) {
		String music = "http://120646e4.nat123.net/wechat/music/mymusic";
		String respContent = "<a href='" + music + "'> 我的歌单... </a>";
		return respContent;
	}

}
