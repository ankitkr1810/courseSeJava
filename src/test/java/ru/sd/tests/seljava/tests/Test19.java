package ru.sd.tests.seljava.tests;

import org.junit.Test;


public class Test19 extends TestBase {
    @Test //Задание номер 19
    public void cart_main() throws InterruptedException {
        // добавить товар в корзину
        app.add_product_to_cart("Red Duck");
        app.add_product_to_cart("Blue Duck");
        app.add_product_to_cart("Green Duck");
        // открыть корзину
        app.open_basket();
        // удалить товары из корзины один за другим
        app.deleteFromCartOneProduct();
        app.deleteFromCartOneProduct();
        app.deleteFromCartOneProduct();
    }

}