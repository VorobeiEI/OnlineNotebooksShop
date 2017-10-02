package entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacksparrow on 13.09.17.
 */
public class GoodList {

    private List<Good> goods = new ArrayList<>();

    public List<Good> getGoods() {
        return goods;
    }

    public void addGood(Good good){
        goods.add(good);
    }

    public void removeGood(Good good){
        goods.remove(good);
    }

    public int getSize() {
        return goods.size();
    }

    public void showAllGoods(){
        for(Good g : goods){
            System.out.println(g);
        }
    }
}
