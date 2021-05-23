package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // @DiscriminatorColumn 에서 정의된 컬럼의 값
public class Book extends Item {
    private String author;
    private String isbn;
}
