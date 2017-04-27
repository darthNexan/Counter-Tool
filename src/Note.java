
/**
 * @author Dennis Guye
 */
public class Note {

    private String line;
    private String resource;

    /**
     * Constructor
     */
    public Note(){
        this.line = "";
        this.resource = "";
    }

    /**
     * Accessor
     * @return
     */
    public String getLine() {
        return line;
    }

    /**
     * Mutator
     * @param line new line to add
     */
    public void setLine(String line) {
        this.line += line + '\n';
    }

    /**
     * Accessor
     * @return
     */
    public String getResource() {
        return resource;
    }

    /**
     * Mutator
     * @param resource
     */
    public void setResource(String resource) {
        this.resource = resource;
    }
}
