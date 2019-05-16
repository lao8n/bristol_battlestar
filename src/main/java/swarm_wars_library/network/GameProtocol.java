package swarm_wars_library.network;

public class GameProtocol {

    /**
     * Message head, beginning symbol
     */
    private int head_data = Constants.HEAD;

    /**
     * Message length, head_data length not included
     */
    private int contentLength;

    /**
     * Message content
     */
    private byte[] content;

    public int getHead_data() {
        return head_data;
    }

    public void setHead_data(int head_data) {
        this.head_data = head_data;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public GameProtocol(int contentLength, byte[] content){
        this.contentLength = contentLength;
        this.content = content;
    }

    @Override
    public String toString(){
        return "<GameProtocol> Length: " + contentLength + ", Content: " +
                new String(content);
    }
}
