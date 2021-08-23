package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id") // 연관관계의 주인
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    // == 생성 메소드 == //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 주문 수량 만큼 재고를 삭제해준다.
        return orderItem;
    }

    // == 비즈니스 로직 == //
    public void cancel() {
        item.addStock(count); // 재고 수량을 원복해준다.
    }

    // == 조회 로직 == //

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
