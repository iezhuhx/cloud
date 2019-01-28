package samples.webflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Service 注解表示 Spring IoC 容器会初始化一个名为 productService 的 Bean，
 * 这个 Bean 可在 Spring Web Flow 的定义中直接访问。
 */
@Service("productService")
public class ProductService {
	/*    products 用于存放多个商品 */
	private Map<Integer, Product> products = new HashMap<Integer, Product>();

	public ProductService() {
		products.put(1, new Product(1, "Bulldog", 1000));
		products.put(2, new Product(2, "Chihuahua", 1500));
		products.put(3, new Product(3, "Labrador", 2000));
	}

	public List<Product> getProducts() {
		return new ArrayList<Product>(products.values());
	}

	public Product getProduct(int productId) {
		return products.get(productId);
	}
}
