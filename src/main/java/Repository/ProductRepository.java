package Repository;

import Models.Product;

import java.util.List;

public interface ProductRepository<T> {
    List<T> list();
    T byId(Long id);
    void save(Product product);

    void update(Product product);
    void delete(Long id);
}
