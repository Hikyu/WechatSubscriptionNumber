package space.kyu.wechat_.common.message.resp;

/**
 * 文本消息
 * @author Administrator
 *
 */
public class RespTextMessage extends RespBaseMessage {  
    // 回复的消息内容   
    private String Content;  

    public String getContent() {  
        return Content;  
    }  

    public void setContent(String content) {  
        Content = content;  
    }  
}
