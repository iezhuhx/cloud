package samples.webflow;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 1951520003958305899L;
    private int id;
    private String description;
    private int price;

    public Product(int id, String description, int price) {
	this.id = id;
	this.description = description;
	this.price = price;
    }

    /**
     * Return property id
     */
    public int getId() {
	return id;
    }

    /**
     * Sets property id
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * Return property description
     */
    public String getDescription() {
	return description;
    }

    /**
     * Sets property description
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * Return property price
     */
    public int getPrice() {
	return price;
    }

    /**
     * Sets property price
     */
    public void setPrice(int price) {
	this.price = price;
    }

    /* Ê¡ÂÔgetterºÍsetter */

}
