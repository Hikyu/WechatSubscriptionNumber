package space.kyu.wechat_.common.message.req;

/**
 * 文本消息
 * @author Administrator
 *
 */
public class ReqTextMessage extends ReqBaseMessage {  
    // 消息内容   
    private String Content;  

    public String getContent() {  
        return Content;  
    }  

    public void setContent(String content) {  
        Content = content;  
    }  
}
