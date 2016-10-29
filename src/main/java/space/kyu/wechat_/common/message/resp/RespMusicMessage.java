package space.kyu.wechat_.common.message.resp;

public class RespMusicMessage extends RespBaseMessage {  
    // 音乐   
    private RespMusic Music;  

    public RespMusic getMusic() {  
        return Music;  
    }  

    public void setMusic(RespMusic music) {  
        Music = music;  
    }  
}