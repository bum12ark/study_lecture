package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingleTonTest {

    @Test
    @DisplayName("싱글톤 빈은 같은 객체를 반환받아야 한다.")
    void singleTonBeanFindTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(SingleTonBean.class);

        SingleTonBean singleTonBean1 = ac.getBean(SingleTonBean.class);
        SingleTonBean singleTonBean2 = ac.getBean(SingleTonBean.class);
        System.out.println("singleTonBean1 = " + singleTonBean1);
        System.out.println("singleTonBean2 = " + singleTonBean2);

        Assertions.assertThat(singleTonBean1).isSameAs(singleTonBean2);

        ac.close();
    }

    @Scope("singleton")
    static class SingleTonBean {
        @PostConstruct
        public void init() {
            System.out.println("SingleTonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingleTonBean.destroy");
        }
    }

}
