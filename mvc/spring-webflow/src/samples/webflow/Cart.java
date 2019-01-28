package samples.webflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//购物车的实现类
public class Cart implements Serializable {

    private static final long serialVersionUID = 7901330827203016310L;
    private Map<Integer, CartItem> map = new HashMap<Integer, CartItem>();
   
    //getItems 用于获取当前购物车里的物品
    public List<CartItem> getItems() {
	return new ArrayList<CartItem>(map.values());
    }
    
    //addItem 用于向购物车添加商品
    public void addItem(Product product) {
	int id = product.getId();
	CartItem item = map.get(id);
	if (item != null)
	    item.increaseQuantity();
	else
	    map.put(id, new CartItem(product, 1));
    }

    //getTotalPrice 用于获取购物车里所有商品的总价格
    public int getTotalPrice() {
	int total = 0;
	for (CartItem item : map.values())
	    total += item.getProduct().getPrice() * item.getQuantity();
	return total;
    }
}
